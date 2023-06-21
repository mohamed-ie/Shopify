package com.example.shopify.ui.bottom_bar.my_account.edit_info.view

import com.example.shopify.ui.common.state.TextFieldState
import com.example.shopify.helpers.UIText

data class EditInfoState(
    val firstName: TextFieldState = TextFieldState(),
    val lastName: TextFieldState = TextFieldState(),
    val remoteError: UIText? = null,
    val isLoading: Boolean = false
)
