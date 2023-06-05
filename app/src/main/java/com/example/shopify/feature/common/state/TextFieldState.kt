package com.example.shopify.feature.common.state

import com.example.shopify.helpers.UIText

data class TextFieldState(
    val value: String = "",
    val isError: Boolean = false,
    val error: UIText = UIText.DynamicString("Help"),
)
