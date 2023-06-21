package com.example.shopify.ui.address.add_address.view

import com.example.shopify.ui.common.state.TextFieldState
import com.example.shopify.helpers.UIText

data class AddAddressState(
    val street: TextFieldState = TextFieldState(),
    val apartment: TextFieldState = TextFieldState(),
    val city: TextFieldState = TextFieldState(),
    val country: TextFieldState = TextFieldState(),
    val state: TextFieldState = TextFieldState(),
    val zip: TextFieldState = TextFieldState(),
    val firstName: TextFieldState = TextFieldState(),
    val lastName: TextFieldState = TextFieldState(),
    val phone: TextFieldState = TextFieldState(),
    val organization: TextFieldState = TextFieldState(),
    val isHomeAddress: Boolean = true,
    val isLoading: Boolean = false,
    val remoteError: UIText? = null
)
