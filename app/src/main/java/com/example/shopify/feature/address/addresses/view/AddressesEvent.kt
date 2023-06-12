package com.example.shopify.feature.address.addresses.view

import com.shopify.graphql.support.ID

sealed interface AddressesEvent {
    object DeleteAddress : AddressesEvent
    class ToggleDeleteConfirmationDialogVisibility(val id: ID?) : AddressesEvent
    class UpdateCartAddress(val id: ID):AddressesEvent
}
