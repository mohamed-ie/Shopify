package com.example.shopify.ui.bottom_bar.cart.checkout.view.event

import com.example.shopify.ui.bottom_bar.cart.checkout.view.state.PaymentMethod
import com.stripe.android.paymentsheet.PaymentSheetResult

sealed interface CheckoutEvent {
    object PlaceOrder : CheckoutEvent
    object HideInvoiceDialog : CheckoutEvent
    object ClearClientSecret : CheckoutEvent

    class PaymentMethodChanged(val method: PaymentMethod) : CheckoutEvent
    class HandlePaymentResult(val result: PaymentSheetResult) : CheckoutEvent
}