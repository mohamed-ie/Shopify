package com.example.shopify.feature.navigation_bar.cart

import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.cart.view.CartState
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartItemEvent
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartItemState
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponEvent
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ShopifyRepository
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    private val _cartItemsState = MutableStateFlow<List<CartItemState>>(listOf())
    val cartItemsState = _cartItemsState.asStateFlow()

    private val _couponState = MutableStateFlow(CartCouponState())
    val couponState = _couponState.asStateFlow()

    fun onCartItemEvent(event: CartItemEvent) {
        when (event) {
            is CartItemEvent.MoveToWishlist -> TODO()
            is CartItemEvent.QuantityChanged -> TODO()
            is CartItemEvent.RemoveItem -> TODO()
            CartItemEvent.ToggleQuantitySelectorVisibility -> TODO()
        }
    }

    fun onCouponEvent(event: CartCouponEvent) {
        when (event) {
            CartCouponEvent.Apply -> TODO()
            CartCouponEvent.Clear -> TODO()
            is CartCouponEvent.ValueChanged -> TODO()
        }
    }

    private fun getCart() {
        repository.getCart()
    }
}