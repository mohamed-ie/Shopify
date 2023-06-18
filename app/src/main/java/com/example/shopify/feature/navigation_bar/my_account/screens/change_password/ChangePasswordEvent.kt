package com.example.shopify.feature.navigation_bar.my_account.screens.change_password

sealed interface ChangePasswordEvent {
    object Change:ChangePasswordEvent
    object TogglePasswordVisibility:ChangePasswordEvent
    object ToggleConfirmPasswordVisibility:ChangePasswordEvent

    class PasswordChanged(val newValue: String) : ChangePasswordEvent
    class ConfirmPasswordChanged(val newValue: String) : ChangePasswordEvent
}
