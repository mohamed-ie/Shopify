package com.example.shopify.feature.navigation_bar.my_account.screens.change_password

import com.example.shopify.feature.navigation_bar.common.state.TextFieldState
import com.example.shopify.helpers.UIText

data class ChangePasswordState(
    val password: TextFieldState = TextFieldState(),
    val confirmPassword: TextFieldState = TextFieldState(),
    val remoteError: UIText? = null,
    val isLoading: Boolean = false
)
