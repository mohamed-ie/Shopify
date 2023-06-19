package com.example.shopify.feature.navigation_bar.my_account.screens.change_phone_number


sealed interface ChangePhoneNumberEvent {
    object Change : ChangePhoneNumberEvent

    class PhoneChanged(val newValue: String) : ChangePhoneNumberEvent
}
