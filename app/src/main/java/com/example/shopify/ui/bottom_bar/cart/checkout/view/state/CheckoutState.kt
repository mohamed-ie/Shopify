package com.example.shopify.ui.bottom_bar.cart.checkout.view.state

import com.example.shopify.helpers.UIText
import com.example.shopify.model.cart.cart.Cart

data class CheckoutState(
    val cart: Cart = Cart(),
    val selectedPaymentMethod: PaymentMethod = PaymentMethod.CreditCard,
    val invoiceUrl: String? = null,
    val isInvoiceDialogVisible: Boolean = false,
    val remoteError: UIText? = null

)
