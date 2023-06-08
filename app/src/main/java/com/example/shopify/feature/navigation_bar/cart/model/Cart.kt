package com.example.shopify.feature.navigation_bar.cart.model

import com.shopify.buy3.Storefront.CartBuyerIdentity
import com.shopify.graphql.support.ID

data class Cart(
    val items: List<CartItem> = listOf(),
    val taxes: String = "",
    val subTotalsPrice: String = "",
    val shippingFee: String = "",

    val totalPrice: String = "",
    val totalQuantity: Int = 0,
    val cartBuyerIdentity: CartBuyerIdentity = CartBuyerIdentity(),
    val cartId: ID = ID("")
)
