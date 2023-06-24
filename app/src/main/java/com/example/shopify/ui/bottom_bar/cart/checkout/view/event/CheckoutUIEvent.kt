package com.example.shopify.ui.bottom_bar.cart.checkout.view.event

sealed interface CheckoutUIEvent {
    object NavigateToOrdersScreen : CheckoutUIEvent
    object NavigateToCreditCardInfoScreen : CheckoutUIEvent
}
