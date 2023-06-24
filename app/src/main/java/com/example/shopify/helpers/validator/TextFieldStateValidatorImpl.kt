package com.example.shopify.helpers.validator

import com.example.shopify.ui.common.state.TextFieldState
import com.example.shopify.helpers.UIText
import javax.inject.Inject


class TextFieldStateValidatorImpl @Inject constructor() : TextFieldStateValidator {
    companion object {
        private const val PHONE_PATTERN = "^\\+?[0-9]{11,13}$"
        private const val ZIP_PATTERN = "^[0-9]{5}(?:-[0-9]{4})?$"
        private const val NAME_PATTERN = "^[a-zA-Z]{3,}$"
    }

    private val zipRegex by lazy { Regex(ZIP_PATTERN) }
    private val phoneRegex by lazy { Regex(PHONE_PATTERN) }
    private val nameRegex by lazy { Regex(NAME_PATTERN) }

    override fun emptyValidation(
        state: TextFieldState,
        errorMessage: UIText
    ): TextFieldState = state.emptyValidation(errorMessage) { state.copy(isError = false) }

    override fun validateZip(
        state: TextFieldState,
        emptyErrorMessage: UIText,
        errorMessage: UIText
    ): TextFieldState = state.emptyValidation(emptyErrorMessage) {
        state.copy(
            isError = !zipRegex.matches(state.value),
            error = errorMessage
        )
    }

    override fun validatePhone(
        state: TextFieldState,
        emptyErrorMessage: UIText,
        errorMessage: UIText
    ): TextFieldState = state.emptyValidation(emptyErrorMessage) {
        state.copy(
            isError = !phoneRegex.matches(state.value),
            error = errorMessage
        )
    }

    override fun validateName(
        state: TextFieldState,
        emptyErrorMessage: UIText,
        errorMessage: UIText
    ): TextFieldState = state.emptyValidation(emptyErrorMessage) {
        state.copy(
            isError = !nameRegex.matches(state.value),
            error = errorMessage
        )
    }

    private fun TextFieldState.emptyValidation(
        errorMessage: UIText,
        then: () -> TextFieldState
    ): TextFieldState {
        return if (value.isBlank())
            copy(isError = true, error = errorMessage)
        else then()
    }
}