package com.example.shopify.ui.screen.cart.model

data class Cart(
    val items: List<CartItem> = listOf(),
    val taxes: String = "",
    val subTotalsPrice: String = "",
    val shippingFee: String = "",
    val totalPrice: String = ""
)
