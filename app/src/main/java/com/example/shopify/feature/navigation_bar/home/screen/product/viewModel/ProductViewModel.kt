package com.example.shopify.ui.screen.Product.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.home.screen.product.model.ProductsState
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    private val state: SavedStateHandle
) : ViewModel() {
    private var _productState = MutableStateFlow(ProductsState())
    val productList = _productState.asStateFlow()
    private var brandProducts: List<BrandProduct> = listOf()

    init {
        state.get<String>("brandName")?.let {
            getProduct(it)
        }
    }

    fun getProduct(brandName: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getProductsByBrandName(brandName).collect {
                when (it) {
                    is Resource.Success -> {
                        brandProducts = it.data
                        updateState()
                    }

                    is Resource.Error -> it.error
                }
            }
        }
    }

    private fun updateState() {
        val prices = brandProducts.map {
            it.brandVariants.price.amount.toFloat()
        }
        _productState.update { oldState ->
            oldState.copy(minPrice = prices.min(), maxPrice = prices.max(), brandProducts = brandProducts.map {brandProduct ->  
                brandProduct.copy(id = brandProduct.id.split('/').last())
            })
        }
    }

    fun updateSliderValue(newValue: Float) {
        val filterProducts = brandProducts.filter {
            it.brandVariants.price.amount.toFloat() > newValue
        }
        _productState.update {
            it.copy(sliderValue = newValue, brandProducts = filterProducts)
        }
    }
}

