package com.example.shopify.feature.navigation_bar.my_account.screens.addresses.view

sealed interface AddressesEvent {
    object DeleteAddress : AddressesEvent
    class ToggleDeleteConfirmationDialogVisibility(val id: String?) : AddressesEvent
}
