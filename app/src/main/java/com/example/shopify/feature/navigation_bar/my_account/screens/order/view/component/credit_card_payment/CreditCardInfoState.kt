package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment

import com.example.shopify.feature.navigation_bar.common.state.TextFieldState

data class CreditCardInfoState(
    val firstNameState: TextFieldState = TextFieldState(),
    val lastNameState: TextFieldState = TextFieldState(),
    val cardNumberState: TextFieldState = TextFieldState(),
    val expireMonthState: TextFieldState = TextFieldState(),
    val expireYearState: TextFieldState = TextFieldState(),
    val ccvState: TextFieldState = TextFieldState(),
)
