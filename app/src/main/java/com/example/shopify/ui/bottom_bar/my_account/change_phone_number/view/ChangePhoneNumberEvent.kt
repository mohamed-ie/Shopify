package com.example.shopify.ui.bottom_bar.my_account.change_phone_number.view


sealed interface ChangePhoneNumberEvent {
    object Change : ChangePhoneNumberEvent

    class PhoneChanged(val newValue: String) : ChangePhoneNumberEvent
}
