package com.example.shopify.ui.screen.cart.view.componenet.cart_item_card

sealed interface CartItemEvent{
    object ToggleQuantitySelectorVisibility : CartItemEvent

    class RemoveItem(val id: String) : CartItemEvent
    class MoveToWishlist(val id: String) : CartItemEvent
    class QuantityChanged(val value: Int) : CartItemEvent
}