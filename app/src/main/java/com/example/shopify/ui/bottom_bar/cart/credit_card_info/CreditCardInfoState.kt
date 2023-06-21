package com.example.shopify.ui.bottom_bar.cart.credit_card_info

import com.example.shopify.helpers.UIText
import com.example.shopify.ui.common.state.TextFieldState

data class CreditCardInfoState(
    val firstNameState: TextFieldState = TextFieldState(),
    val lastNameState: TextFieldState = TextFieldState(),
    val cardNumberState: TextFieldState = TextFieldState(),
    val expireMonthState: TextFieldState = TextFieldState(),
    val expireYearState: TextFieldState = TextFieldState(),
    val ccvState: TextFieldState = TextFieldState(),
    val remoteError: UIText? = null
)
