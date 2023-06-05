package com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment

interface PaymentStrategy<I, O> {
    fun pay(paymentInfo: I): O
}