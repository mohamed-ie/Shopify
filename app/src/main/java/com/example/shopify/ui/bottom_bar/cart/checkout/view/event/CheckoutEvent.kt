package com.example.shopify.ui.bottom_bar.cart.checkout.view.event

import com.example.shopify.ui.bottom_bar.cart.checkout.view.state.PaymentMethod

sealed interface CheckoutEvent {
    object PlaceOrder : CheckoutEvent
    object HideInvoiceDialog : CheckoutEvent

    class PaymentMethodChanged(val method: PaymentMethod) : CheckoutEvent
}