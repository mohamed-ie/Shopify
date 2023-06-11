package com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view

sealed interface MyAccountEvent {
    object ToggleSignOutConfirmDialogVisibility : MyAccountEvent
    object SignOut : MyAccountEvent
    object ToggleCurrencyRadioGroupModalSheetVisibility : MyAccountEvent

    class CurrencyChanged(val newValue: String) : MyAccountEvent
}
