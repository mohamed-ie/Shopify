package com.example.shopify.ui.auth.login.ui

import com.example.shopify.ui.auth.common.AuthTextFieldData

data class LoginUiState (
    val email: AuthTextFieldData = AuthTextFieldData(),
    val password: AuthTextFieldData = AuthTextFieldData()
)

