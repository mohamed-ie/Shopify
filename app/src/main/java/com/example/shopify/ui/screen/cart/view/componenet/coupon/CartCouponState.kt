package com.example.shopify.ui.screen.cart.view.componenet.coupon

data class CartCouponState(
    val errorVisible: Boolean = false,
    val coupon: String = "",
    val isLoading: Boolean = false
)
