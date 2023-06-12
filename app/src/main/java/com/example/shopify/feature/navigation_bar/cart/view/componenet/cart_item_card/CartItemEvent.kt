package com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card

sealed interface CartItemEvent {
    class ToggleQuantitySelectorVisibility(val index: Int) : CartItemEvent
    class RemoveLine(val index: Int) : CartItemEvent
    class MoveToWishlist(val index: Int) : CartItemEvent
    class QuantityChanged(val index: Int, val quantity: Int) : CartItemEvent
}