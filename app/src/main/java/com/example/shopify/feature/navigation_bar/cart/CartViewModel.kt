package com.example.shopify.feature.navigation_bar.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartItemEvent
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartLineState
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponEvent
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponState
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.helpers.Resource
import com.shopify.graphql.support.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(Cart())
    val state = _state.asStateFlow()

    private val _cartLinesState = MutableStateFlow(mutableStateListOf<CartLineState>())
    val cartLinesState = _cartLinesState.asStateFlow()

    private val _couponState = MutableStateFlow(CartCouponState())
    val couponState = _couponState.asStateFlow()

    fun loadCart() = viewModelScope.launch(defaultDispatcher) {
        //(repository as? ShopifyRepositoryImpl)?.fireStoreManager?.getCurrentCartId("mohammedie98@gmail.com")
        toLoadingScreenState()
        handleCartResource(repository.getCart())
        checkIsLoggedIn()
    }

    private fun handleCartResource(resource: Resource<Cart?>) =
        when (resource) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                resource.data?.run {
                    _state.update { this }
                    _cartLinesState.update { List(this.lines.size) { CartLineState() }.toMutableStateList() }
                    _couponState.update { it.copy(errorVisible = this.couponError != null) }
                }
                toStableScreenState()
            }
        }

    private fun checkIsLoggedIn() {
        repository.isLoggedIn()
            .onEach { _state.update { cart -> cart.copy(isLoggedIn = it) } }
            .launchIn(viewModelScope)
    }

    fun onCartItemEvent(event: CartItemEvent) {
        when (event) {
            is CartItemEvent.MoveToWishlist ->
                removeCartLineAndAddToWishList(index = event.index)

            is CartItemEvent.QuantityChanged ->
                changeCartLineQuantity(event.index, event.quantity)

            is CartItemEvent.RemoveLine ->
                removeCartLine(event.index)

            is CartItemEvent.ToggleQuantitySelectorVisibility ->
                _cartLinesState.update(event.index) { it.copy(isChooseQuantityOpen = it.isChooseQuantityOpen.not()) }

        }
    }

    private fun addCartItemToWishList(productID: ID) {
        viewModelScope.launch {
            repository.addProductWishListById(productID)
        }
    }

    private fun changeCartLineQuantity(index: Int, quantity: Int) =
        viewModelScope.launch(defaultDispatcher) {
            _cartLinesState.update(index) {
                it.copy(
                    isChangingQuantity = true,
                    isChooseQuantityOpen = false
                )
            }
            val cartLineId = _state.value.lines[index].productVariantID.toString()
            when (val resource = repository.changeCartLineQuantity(cartLineId, quantity)) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> handleCartResource(resource)
            }
        }

    private fun removeCartLine(index: Int) = viewModelScope.launch(defaultDispatcher) {
        _cartLinesState.update(index) { it.copy(isRemoving = it.isRemoving.not()) }
        val cartLineId = _state.value.lines[index].productVariantID.toString()
        when (val resource = repository.removeCartLines(cartLineId)) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> handleCartResource(resource)
        }
    }

    private fun removeCartLineAndAddToWishList(index: Int) =
        viewModelScope.launch(defaultDispatcher) {
            _cartLinesState.update(index) { it.copy(isMovingToWishlist = it.isMovingToWishlist.not()) }
            val cartLineId = _state.value.lines[index].id.toString()
            when (val resource = repository.removeCartLines(cartLineId)) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    _state.value.lines[index].cartProduct.id.let { productId ->
                        addCartItemToWishList(
                            productId
                        )
                    }
                    handleCartResource(resource)
                }
            }
        }

    fun onCouponEvent(event: CartCouponEvent) {
        when (event) {
            CartCouponEvent.Apply -> {
                applyCoupon()
            }

            CartCouponEvent.Clear ->
                _couponState.update { it.copy(coupon = "") }

            is CartCouponEvent.ValueChanged ->
                _couponState.update { it.copy(coupon = event.value) }
        }
    }

    private fun applyCoupon() = viewModelScope.launch(defaultDispatcher) {
//        handleCartResource(repository.applyCouponToCart(couponState.value.coupon))
        _couponState.update { it.copy(errorVisible = false, isLoading = true) }
        delay(1500)
        _couponState.update { it.copy(errorVisible = true, isLoading = false) }
    }
}

private fun MutableStateFlow<SnapshotStateList<CartLineState>>.update(
    index: Int,
    state: (oldState: CartLineState) -> CartLineState
) = update { oldList ->
    oldList.apply { add(index, removeAt(index).run { state(this) }) }
}

