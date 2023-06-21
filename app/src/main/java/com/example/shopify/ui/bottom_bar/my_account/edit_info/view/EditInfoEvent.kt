package com.example.shopify.ui.bottom_bar.my_account.edit_info.view

sealed interface EditInfoEvent {
    object Change: EditInfoEvent

    class FirstNameChanged(val newValue: String) : EditInfoEvent
    class LastNameChanged(val newValue: String) : EditInfoEvent
}
