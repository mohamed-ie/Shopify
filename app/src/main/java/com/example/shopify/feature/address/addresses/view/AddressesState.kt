package com.example.shopify.feature.address.addresses.view

import com.example.shopify.feature.address.addresses.model.MyAccountMinAddress

data class AddressesState(
    val addresses: List<MyAccountMinAddress> = emptyList(),
    val isDeleteDialogVisible: Boolean = false
)
