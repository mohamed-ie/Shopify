package com.example.shopify.ui.screen.auth.common

data class RegistrationUiState (
    val firstName:AuthTextFieldData = AuthTextFieldData(),
    val secondName:AuthTextFieldData = AuthTextFieldData(),
    val email:AuthTextFieldData = AuthTextFieldData(),
    val phone:AuthTextFieldData = AuthTextFieldData(),
    val password:AuthTextFieldData = AuthTextFieldData()
)

data class ErrorAuthUiState(
    val error:String = "",
    val isError:Boolean = false
)