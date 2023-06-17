package com.example.shopify.helpers.validator

import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.state.TextFieldState
import com.example.shopify.helpers.UIText

interface TextFieldStateValidator {
    fun emptyValidation(
        state: TextFieldState,
        errorMessage: UIText = UIText.StringResource(R.string.required)
    ): TextFieldState

    fun validateZip(
        state: TextFieldState,
        emptyErrorMessage: UIText = UIText.StringResource(R.string.required),
        errorMessage: UIText = UIText.StringResource(R.string.not_valid)
    ): TextFieldState

    fun validatePhone(
        state: TextFieldState,
        emptyErrorMessage: UIText = UIText.StringResource(R.string.required),
        errorMessage: UIText = UIText.StringResource(R.string.not_valid)
    ): TextFieldState

    fun validateName(
        state: TextFieldState,
        emptyErrorMessage: UIText = UIText.StringResource(R.string.required),
        errorMessage: UIText = UIText.StringResource(R.string.not_valid)
    ): TextFieldState

}