package com.example.shopify.feature.navigation_bar.my_account.screens.change_phone_number

import com.example.shopify.feature.navigation_bar.common.state.TextFieldState
import com.example.shopify.helpers.UIText

data class ChangePhoneNumberState(
    val phone: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val remoteError: UIText? = null
)
