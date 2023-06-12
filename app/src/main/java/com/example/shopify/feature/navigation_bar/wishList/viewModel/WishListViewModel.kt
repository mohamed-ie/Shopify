package com.example.shopify.feature.navigation_bar.wishList.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.helpers.Resource
import com.shopify.graphql.support.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WishListViewModel @Inject constructor(
    private val repository: ShopifyRepository,
) : BaseScreenViewModel() {

    private val _productsState = MutableStateFlow(mutableStateListOf<Product>())
    val productsState= _productsState.asStateFlow()

    private val _dialogVisibilityState = MutableStateFlow(false)
    val dialogVisibilityState= _dialogVisibilityState.asStateFlow()

    val deletedProduct = MutableStateFlow(ID(""))


    fun getWishListProducts(){
        toLoadingScreenState()
        _productsState.value.clear()
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

    fun removeWishProduct(productId:ID) {
        _dialogVisibilityState.value = true
        deletedProduct.value = productId;
    }

    fun dismissDeleteDialog() {
        _dialogVisibilityState.value = false
    }

    fun confirmDeletedProduct(){
        viewModelScope.launch {
            repository.removeProductWishListById(deletedProduct.value)
            _dialogVisibilityState.value = false
            getWishListProducts()
        }
    }




}