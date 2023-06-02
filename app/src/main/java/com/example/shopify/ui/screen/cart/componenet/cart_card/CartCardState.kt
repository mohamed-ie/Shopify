package com.example.shopify.ui.screen.cart.componenet.cart_card

import com.example.shopify.ui.screen.cart.model.CartItem

data class CartCardState(
    val chooseQuantityOpened: Boolean = false,
    val selectedQuantity: Int = 1,
    val cartItem: CartItem,
    val isRemoving: Boolean = false,
    val isAddingToWishlist: Boolean = false,
    val isChangingQuantity: Boolean = false
)