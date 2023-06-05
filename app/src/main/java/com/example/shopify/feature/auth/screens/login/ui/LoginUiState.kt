package com.example.shopify.feature.auth.screens.login.ui

import com.example.shopify.feature.auth.screens.common.AuthTextFieldData

data class LoginUiState (
    val email: AuthTextFieldData = AuthTextFieldData(),
    val password: AuthTextFieldData = AuthTextFieldData()
)

