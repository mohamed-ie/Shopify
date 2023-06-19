package com.example.shopify.feature.navigation_bar.my_account.screens.edit_info

sealed interface EditInfoEvent {
    object Change: EditInfoEvent

    class FirstNameChanged(val newValue: String) : EditInfoEvent
    class LastNameChanged(val newValue: String) : EditInfoEvent
}
