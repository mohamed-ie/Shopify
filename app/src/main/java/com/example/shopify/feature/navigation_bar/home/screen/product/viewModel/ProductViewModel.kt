package com.example.shopify.feature.navigation_bar.home.screen.product.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.home.screen.product.model.ProductsState
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import com.shopify.graphql.support.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ShopifyRepository, private val state: SavedStateHandle
) : BaseScreenViewModel() {
    private var _productState = MutableStateFlow(ProductsState())
    val productList = _productState.asStateFlow()
    private var brandProducts: List<BrandProduct> = listOf()

    init {
        state.get<String>("brandName")?.let {
            getProduct(it)
        }
    }

    private fun getProduct(brandName: String) = viewModelScope.launch(Dispatchers.Default) {

        when (val it = repository.getProductsByBrandName(brandName)) {
            is Resource.Success -> {
                brandProducts = it.data
                updateState()
                toStableScreenState()
            }

            is Resource.Error ->
                toErrorScreenState()
        }
    }

    private fun updateState() {
        val prices = brandProducts.map {
            it.price.amount.toFloat()
        }
        _productState.update { oldState ->
            oldState.copy(
                minPrice = prices.min(), maxPrice = prices.max(), brandProducts = brandProducts
            )
        }
    }

    fun updateSliderValue(newValue: Float) {
        val filterProducts = brandProducts.filter {
            it.price.amount.toFloat() > newValue
        }
        _productState.update {
            it.copy(sliderValue = newValue, brandProducts = filterProducts)
        }
    }

    fun onFavourite(id: ID, favourite: Boolean) {
        if (favourite) viewModelScope.launch { repository.removeProductWishListById(id) }
        else viewModelScope.launch { repository.addProductWishListById(id) }
    }

    fun hideDialog() {
    }

    fun apply() {

    }
}

