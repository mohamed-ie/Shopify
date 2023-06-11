package com.example.shopify.feature.navigation_bar.my_account.screens.order.helpers

import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment.CreditCardInfoState
import kotlinx.coroutines.flow.StateFlow

interface CreditCardInfoStateHandler {
    val creditCardInfoState: StateFlow<CreditCardInfoState>

    fun isValid(): Boolean
    fun updateFirstName(newValue: String)
    fun updateLastName(newValue: String)
    fun updateCardNumber(newValue: String)
    fun updateExpireMonth(newValue: String)
    fun updateExpireYear(newValue: String)
    fun updateCCV(newValue: String)
}