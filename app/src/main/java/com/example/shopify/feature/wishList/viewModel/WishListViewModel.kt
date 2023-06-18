package com.example.shopify.feature.wishList.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.wishList.view.WishedBottomSheetState
import com.example.shopify.feature.wishList.view.WishedProductState
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

    private val _productsState = MutableStateFlow(mutableStateListOf<WishedProductState>())
    val productsState= _productsState.asStateFlow()

    private val _wishedBottomSheetState = MutableStateFlow(WishedBottomSheetState())
    val wishedBottomSheetState = _wishedBottomSheetState.asStateFlow()

    private val _dialogVisibilityState = MutableStateFlow(false)
    val dialogVisibilityState= _dialogVisibilityState.asStateFlow()

    private val deletedProduct = MutableStateFlow(ID(""))


    fun getWishListProducts(){
        toLoadingScreenState()
        _productsState.value.clear()
        repository.getShopifyProductsByWishListIDs().onEach {productResource ->
            when(productResource){
                is Resource.Error -> {toErrorScreenState()}
                is Resource.Success -> {
                    toStableScreenState()
                    productResource.data?.let { _productsState.value.add(WishedProductState(product = it)) }
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

    fun addToCart(productIndex: Int){
        _productsState.value[productIndex].also {wishedProduct ->
            viewModelScope.launch {
                _productsState.value[productIndex] = wishedProduct.copy(isAddingToCard = true)
                wishedProduct.product.variants[0].id?.let {variantId ->
                    when(repository.addToCart(variantId.toString(),1)){
                        is Resource.Error -> {}
                        is Resource.Success -> {
                            _productsState.value[productIndex] = wishedProduct.copy(isAddingToCard = false)
                            _wishedBottomSheetState.value = _wishedBottomSheetState.value.copy(
                                isAdded = true,
                                expandBottomSheet = true,
                                productTitle = wishedProduct.product.title
                            )
                            sendTotalCart(repository.getCart())
                        }
                    }
                }
            }
        }
    }

    fun sendContinueShopping(){
        _wishedBottomSheetState.value = _wishedBottomSheetState.value.copy(expandBottomSheet = false)
    }
    private fun sendTotalCart(response:Resource<Cart?>){
        when(response){
            is Resource.Error -> {}
            is Resource.Success -> {
                _wishedBottomSheetState.value = _wishedBottomSheetState.value.copy(
                    isTotalPriceLoaded = true,
                    totalCartPrice = response.data?.totalPrice ?: ""
                )

            }
        }
    }
}