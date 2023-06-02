package com.example.shopify.ui.screen.cart.componenet.coupon

data class CouponCardState(
    val errorVisible: Boolean = false,
    val coupon: String = "",
    val isLoading: Boolean = false
)
