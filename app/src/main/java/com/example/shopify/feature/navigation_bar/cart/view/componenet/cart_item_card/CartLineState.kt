package com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card

data class CartLineState(
    val isChooseQuantityOpen: Boolean = false,
    val isRemoving: Boolean = false,
    val isMovingToWishlist: Boolean = false,
    val isChangingQuantity: Boolean = false
)