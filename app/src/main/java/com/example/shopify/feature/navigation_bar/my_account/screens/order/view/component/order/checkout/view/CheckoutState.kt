package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view

import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.PaymentMethod

data class CheckoutState(
    val cart: Cart = Cart(),
    val selectedPaymentMethod: PaymentMethod = PaymentMethod.CreditCard,
    val invoiceUrl: String? = null,
    val isInvoiceDialogVisible: Boolean = false,
    val remoteError: String? = null

)
