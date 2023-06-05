package com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon

data class CartCouponState(
    val errorVisible: Boolean = false,
    val coupon: String = "",
    val isLoading: Boolean = false
)
