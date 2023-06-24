package com.example.shopify.ui.bottom_bar.my_account.change_password.view

import com.example.shopify.ui.common.state.TextFieldState
import com.example.shopify.helpers.UIText

data class ChangePasswordState(
    val password: TextFieldState = TextFieldState(),
    val confirmPassword: TextFieldState = TextFieldState(),
    val remoteError: UIText? = null,
    val isLoading: Boolean = false
)
