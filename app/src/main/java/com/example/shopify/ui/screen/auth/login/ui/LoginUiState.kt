package com.example.shopify.ui.screen.auth.login.ui

import com.example.shopify.ui.screen.auth.common.AuthTextFieldData

data class LoginUiState (
    val email:AuthTextFieldData = AuthTextFieldData(),
    val password:AuthTextFieldData = AuthTextFieldData()
)

