package com.example.shopify.feature.address.addresses.view

sealed interface AddressesEvent {
    object DeleteAddress : AddressesEvent
    class ToggleDeleteConfirmationDialogVisibility(val index: Int?) : AddressesEvent
    class UpdateAddress(val index: Int):AddressesEvent
}
