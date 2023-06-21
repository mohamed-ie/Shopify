package com.example.shopify.ui.bottom_bar.cart.cart.view.componenet.cart_item_card

data class CartLineState(
    val isChooseQuantityOpen: Boolean = false,
    val isRemoving: Boolean = false,
    val isMovingToWishlist: Boolean = false,
    val isChangingQuantity: Boolean = false
)