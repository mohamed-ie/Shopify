package com.example.shopify.ui.screen.Product.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.feature.navigation_bar.home.screen.Product.model.Product
import com.example.shopify.feature.navigation_bar.home.screen.Product.model.ProductsState
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
    private var products: List<Product> = listOf()

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
                        products = it.data
                        updateState()
                    }

                    is Resource.Error -> it.error
                }
            }
        }
    }

    private fun updateState() {
        val prices = products.map {
            it.variants.price.amount.toFloat()
        }
        _productState.update { oldState ->
            oldState.copy(minPrice = prices.min(), maxPrice = prices.max(), products = products)
        }
    }

    fun updateSliderValue(newValue: Float) {
        val filterProducts = products.filter {
            it.variants.price.amount.toFloat() > newValue
        }
        _productState.update {
            it.copy(sliderValue = newValue, products = filterProducts)
        }
    }
}

