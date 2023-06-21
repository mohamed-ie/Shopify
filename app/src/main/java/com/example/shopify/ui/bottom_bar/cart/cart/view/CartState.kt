package com.example.shopify.ui.bottom_bar.cart.cart.view

import com.example.shopify.model.cart.cart.Cart

data class CartState(
    val cart: Cart = Cart(),
    val isLoggedIn: Boolean = false
)
