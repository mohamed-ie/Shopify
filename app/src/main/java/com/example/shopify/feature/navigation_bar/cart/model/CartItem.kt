package com.example.shopify.feature.navigation_bar.cart.model

data class CartItem(
    val id: String,
    val discount: String,
    val quantity: Int,
    val availableQuantity: Int,
    val cartProduct: CartProduct,
    val priceAfterDiscount: String,
    val priceBeforeDiscount: String,
)
