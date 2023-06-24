package com.example.shopify.ui.bottom_bar.my_account.change_password.view

sealed interface ChangePasswordEvent {
    object Change:
        ChangePasswordEvent
    object TogglePasswordVisibility:
        ChangePasswordEvent
    object ToggleConfirmPasswordVisibility:
        ChangePasswordEvent

    class PasswordChanged(val newValue: String) :
        ChangePasswordEvent
    class ConfirmPasswordChanged(val newValue: String) :
        ChangePasswordEvent
}
