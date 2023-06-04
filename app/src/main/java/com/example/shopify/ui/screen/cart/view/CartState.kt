package com.example.shopify.ui.screen.cart.view

import com.example.shopify.ui.screen.cart.model.Cart

data class CartState(
    val cart: Cart = Cart(),
    val address: String = ""
)
