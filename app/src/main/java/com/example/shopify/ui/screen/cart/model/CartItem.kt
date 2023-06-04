package com.example.shopify.ui.screen.cart.model

data class CartItem(
    val id: String,
    val discount: String,
    val quantity: Int,
    val availableQuantity: Int,
    val product: Product,
    val priceAfterDiscount: String,
    val priceBeforeDiscount: String,
)
