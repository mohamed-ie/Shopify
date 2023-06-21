package com.example.shopify.feature.navigation_bar.cart

import com.example.shopify.feature.navigation_bar.cart.model.Cart

data class CartState(
    val cart: Cart = Cart(),
    val isLoggedIn: Boolean = false
)
