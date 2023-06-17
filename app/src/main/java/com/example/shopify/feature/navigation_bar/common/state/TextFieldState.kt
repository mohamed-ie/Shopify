package com.example.shopify.feature.navigation_bar.common.state

import com.example.shopify.helpers.UIText

data class TextFieldState(
    val value: String = "",
    val isError: Boolean = false,
    val error: UIText = UIText.DynamicString("Help"),
) {
    companion object {
        const val NUMBERS_PATTERN = "[0-9]*"
        const val ZIP_PATTERN = "[0-9]*-?[0-9]*]"
        const val CHARTERS_PATTERN = "[a-zA-Z]*"
    }

    private val numbersRegex by lazy { Regex(NUMBERS_PATTERN) }
    private val zipRegex by lazy { Regex(ZIP_PATTERN) }
    private val charactersRegex by lazy { Regex(CHARTERS_PATTERN) }
    fun updateNumbersValue(newValue: String): TextFieldState {
        if (numbersRegex.matches(newValue))
            return copy(value = newValue)
        return this
    }

    fun updateCharactersValue(newValue: String): TextFieldState {
        if (charactersRegex.matches(newValue))
            return copy(value = newValue)
        return this
    }
    fun updateValue(newValue: String): TextFieldState {
            return copy(value = newValue)
    }
    fun updateZipValue(newValue: String): TextFieldState {
        if (zipRegex.matches(newValue))
            return copy(value = newValue)
        return this
    }
}
