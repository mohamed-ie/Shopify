package com.example.shopify.feature.navigation_bar.wishList.viewModel

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.remote.FireStoreManager
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.helpers.Resource
import com.example.shopify.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class WishListViewModel @Inject constructor(
    private val repository: ShopifyRepository,
) : BaseScreenViewModel() {

    private val _productsState = MutableStateFlow(mutableListOf<Product>())
    val productsState = _productsState.asStateFlow()

    init {
        getWishListProducts()
    }

    fun getWishListProducts(){
        repository.getShopifyProductsByWishListIDs().onEach {productResource ->
            when(productResource){
                is Resource.Error -> {toErrorScreenState()}
                is Resource.Success -> {
                    toStableScreenState()
                    _productsState.value.add(productResource.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}