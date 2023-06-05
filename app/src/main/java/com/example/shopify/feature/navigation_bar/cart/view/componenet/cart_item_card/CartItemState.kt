package com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card

data class CartItemState(
    val chooseQuantityOpened: Boolean = false,
    val selectedQuantity: Int = 1,
    val isRemoving: Boolean = false,
    val isAddingToWishlist: Boolean = false,
    val isChangingQuantity: Boolean = false
)