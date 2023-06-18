package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view

import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.PaymentMethod

sealed interface CheckoutEvent {
    object PlaceOrder : CheckoutEvent
    object HideInvoiceDialog : CheckoutEvent

    class PaymentMethodChanged(val method: PaymentMethod) : CheckoutEvent
}