package com.example.shopify.ui.address.addresses.view

import com.shopify.buy3.Storefront.MailingAddress

data class AddressesState(
    val addresses: List<MailingAddress> = emptyList(),
    val isDeleteDialogVisible: Boolean = false
)
