package com.example.shopify.feature.navigation_bar.my_account.screens.addresses.view

import com.example.shopify.feature.navigation_bar.my_account.screens.addresses.model.MyAccountMinAddress

data class AddressesState(
    val addresses: List<MyAccountMinAddress> = emptyList(),
    val isDeleteDialogVisible: Boolean = false
)
