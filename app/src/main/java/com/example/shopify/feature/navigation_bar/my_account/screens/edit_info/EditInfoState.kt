package com.example.shopify.feature.navigation_bar.my_account.screens.edit_info

import com.example.shopify.feature.navigation_bar.common.state.TextFieldState
import com.example.shopify.helpers.UIText

data class EditInfoState(
    val firstName: TextFieldState = TextFieldState(),
    val lastName: TextFieldState = TextFieldState(),
    val remoteError: UIText? = null,
    val isLoading: Boolean = false
)
