package com.example.shopify.helpers.cart

import com.example.shopify.helpers.UIText
import com.example.shopify.ui.bottom_bar.cart.credit_card_info.CreditCardInfoState
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
    fun updateRemoteError(remoteError: UIText.DynamicString?)
}