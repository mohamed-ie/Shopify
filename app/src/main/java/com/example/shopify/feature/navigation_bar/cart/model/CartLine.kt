package com.example.shopify.feature.navigation_bar.cart.model

import com.shopify.graphql.support.ID

data class CartLine(
    val productVariantID: ID, // for checkout
    val id: ID,
    val price: String = "",
    val currencyCode: String = "EGP",
    val quantity: Int,
    val availableQuantity: Int,
    val cartProduct: CartProduct,
)
