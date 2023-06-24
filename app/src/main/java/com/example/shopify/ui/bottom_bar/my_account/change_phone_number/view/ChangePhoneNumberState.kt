package com.example.shopify.ui.bottom_bar.my_account.change_phone_number.view

import com.example.shopify.ui.common.state.TextFieldState
import com.example.shopify.helpers.UIText

data class ChangePhoneNumberState(
    val phone: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val remoteError: UIText? = null
)
