package com.example.shopify.ui.bottom_bar.my_account.my_account.view

sealed interface MyAccountEvent {
    object ToggleSignOutConfirmDialogVisibility : MyAccountEvent
    object SignOut : MyAccountEvent
    object ToggleCurrencyRadioGroupModalSheetVisibility : MyAccountEvent

    class CurrencyChanged(val newValue: String) : MyAccountEvent
}
