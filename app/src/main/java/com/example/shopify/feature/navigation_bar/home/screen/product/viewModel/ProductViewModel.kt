package com.example.shopify.feature.navigation_bar.home.screen.product.viewModel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.home.screen.product.model.ProductsState
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    private val state: SavedStateHandle,
) : BaseScreenViewModel() {
    private var _productState = MutableStateFlow(ProductsState())
    val productState = _productState.asStateFlow()
    private var brandProducts: List<BrandProduct> = listOf()

    init {
        setProductBrandId()
        isLoggedIn()
    }


    fun getProduct() = viewModelScope.launch(Dispatchers.Default) {
        when (val it = repository.getProductsByBrandName(_productState.value.brandProductId)) {
            is Resource.Success -> {
                brandProducts = it.data
                updateState()
                toStableScreenState()
            }

            is Resource.Error ->
                toErrorScreenState()
        }
    }

    private fun isLoggedIn() {
        viewModelScope.launch(Dispatchers.Main) {
            _productState.update { productsState ->
                productsState.copy(isLoggedIn = repository.isLoggedIn().first())
            }

        }
    }

    private fun setProductBrandId() {
        val brandName: String = state.get<String>("brandName") ?: "VANS"
        brandName.also {
            _productState.update { productsState ->
                productsState.copy(brandProductId = it)
            }
        }
    }

    private fun updateState() {
        val prices = brandProducts.map {
            it.price.amount.toFloat()
        }
        _productState.update { oldState ->
            oldState.copy(
                minPrice = prices.min()-1,
                maxPrice = prices.max()+1,
                brandProducts = brandProducts.toMutableStateList()
            )
        }
    }

    fun updateSliderValue(newValue: Float) {
        val filterProducts = brandProducts.filter {
            it.price.amount.toFloat() > newValue
        }
        _productState.update {
            it.copy(sliderValue = newValue, brandProducts = filterProducts.toMutableStateList())
        }
    }

    fun onFavourite(index: Int) {
        viewModelScope.launch {
            _productState.value.brandProducts[index].also { brandProduct ->
                if (brandProduct.isFavourite) {
                    repository.removeProductWishListById(brandProduct.id)
                } else
                    repository.addProductWishListById(brandProduct.id)

                _productState.update { productState ->
                    productState.brandProducts[index] =
                        productState.brandProducts[index].copy(isFavourite = !brandProduct.isFavourite)
                    productState.copy(brandProducts = productState.brandProducts)
                }
            }
        }
    }

    fun hideDialog() {
    }

    fun apply() {

    }
}

