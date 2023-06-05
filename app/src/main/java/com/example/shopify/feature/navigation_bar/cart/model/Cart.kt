package com.example.shopify.feature.navigation_bar.cart.model

data class Cart(
    val items: List<CartItem> = listOf(),
    val taxes: String = "",
    val subTotalsPrice: String = "",
    val shippingFee: String = "",
    val totalPrice: String = ""
)
