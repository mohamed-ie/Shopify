package com.example.shopify.feature.navigation_bar.my_account.screens.order.helpers

import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.state.TextFieldState
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment.CreditCardInfoState
import com.example.shopify.helpers.UIText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class CreditCardInfoStateHandlerImpl @Inject constructor() : CreditCardInfoStateHandler {
    companion object {
        private const val CREDIT_CARD_PATTERN = "^(?:4[0-9]{12}(?:[0-9]{3})?" + //visa
                "|(?:5[1-5][0-9]{2}" + //mastercard
                "|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12})$"//mastercard
        private const val EXPIRE_MONTH_PATTERN = "^(0[1-9]|1[012])$"
        private const val EXPIRE_YEAR_PATTERN = "^[0-9]{4}$"
        private const val CCV_PATTERN = "^[0-9]{3,4}$"
    }

    private var firstNameState = TextFieldState()
    private var lastNameState = TextFieldState()
    private var cardNumberState = TextFieldState()
    private var expireMonthState = TextFieldState()
    private var expireYearState = TextFieldState()
    private var ccvState = TextFieldState()

    private val _creditCardInfoState = MutableStateFlow(CreditCardInfoState())
    override val creditCardInfoState = _creditCardInfoState.asStateFlow()

    private val stateUpdater by lazy { StateUpdater() }
    private val validator by lazy { StateValidator() }

    override fun isValid(): Boolean {
        validate()
        return !lastNameState.isError
                && !firstNameState.isError
                && !cardNumberState.isError
                && !expireMonthState.isError
                && !expireYearState.isError
                && !ccvState.isError
    }

    private fun validate() {
        validator.validateFirstName()
        validator.validateLastName()
        validator.validateCardNumber()
        validator.validateExpireMonth()
        validator.validateExpireYear()
        validator.validateCCV()
    }

    override fun updateFirstName(newValue: String) =
        stateUpdater.updateFirstName(newValue)


    override fun updateLastName(newValue: String) =
        stateUpdater.updateLastName(newValue)


    override fun updateCardNumber(newValue: String) =
        stateUpdater.updateCardNumber(newValue)

    override fun updateExpireMonth(newValue: String) =
        stateUpdater.updateExpireMonth(newValue)

    override fun updateExpireYear(newValue: String) {
        stateUpdater.updateExpireYear(newValue)
    }

    override fun updateCCV(newValue: String) =
        stateUpdater.updateCCV(newValue)

    override fun updateRemoteError(remoteError: UIText.DynamicString?) {
        _creditCardInfoState.update { it.copy(remoteError = remoteError) }
    }

    private inner class StateUpdater {
        private val nameRegex by lazy { Regex("[a-zA-Z]*") }
        private val numberRegex by lazy { Regex("[0-9]*") }
        fun updateFirstName(newValue: String) {
            if (!nameRegex.matches(newValue))
                return
            firstNameState = firstNameState.copy(value = newValue.trimStart())
            _creditCardInfoState.updateFirstName()
        }

        fun updateLastName(newValue: String) {
            if (!nameRegex.matches(newValue))
                return
            lastNameState = lastNameState.copy(value = newValue)
            _creditCardInfoState.updateLastName()
        }

        fun updateCardNumber(newValue: String) {
            if (!numberRegex.matches(newValue))
                return
            cardNumberState = cardNumberState.copy(value = newValue)
            _creditCardInfoState.updateCardNumber()
        }

        fun updateExpireMonth(newValue: String) {
            if (!numberRegex.matches(newValue))
                return
            expireMonthState = expireMonthState.copy(value = newValue)
            _creditCardInfoState.updateExpireMonth()
        }

        fun updateExpireYear(newValue: String) {
            if (!numberRegex.matches(newValue))
                return
            expireYearState = expireYearState.copy(value = newValue)
            _creditCardInfoState.updateExpireYear()
        }

        fun updateCCV(newValue: String) {
            if (!numberRegex.matches(newValue))
                return
            ccvState = ccvState.copy(value = newValue)
            _creditCardInfoState.updateCCV()
        }
    }

    private inner class StateValidator {
        private val cardNumberRegex by lazy { Regex(CREDIT_CARD_PATTERN) }
        private val expireMonthRegex by lazy { Regex(EXPIRE_MONTH_PATTERN) }
        private val expireYearRegex by lazy { Regex(EXPIRE_YEAR_PATTERN) }
        private val ccvRegex by lazy { Regex(CCV_PATTERN) }


        fun validateFirstName() = firstNameState.apply {
            emptyValidation { this }
            _creditCardInfoState.updateFirstName()
        }

        fun validateLastName() = lastNameState.apply {
            emptyValidation { this }
            _creditCardInfoState.updateLastName()
        }

        fun validateCardNumber() = cardNumberState.apply {
            emptyValidation {
                val cardNumber = value.replace("-", "")
                val isError = cardNumberRegex.matches(cardNumber)
                copy(
                    isError = isError,
                    error = UIText.StringResource(R.string.enter_valid_card_number)
                )
            }
            _creditCardInfoState.updateCardNumber()
        }

        fun validateExpireMonth() = expireMonthState.apply {
            emptyValidation {
                val expiry = value
                val isError = expireMonthRegex.matches(expiry)
                copy(
                    isError = isError,
                    error = UIText.StringResource(R.string.expire_month_must_be_in_form)
                )
            }
            _creditCardInfoState.updateExpireMonth()
        }

        fun validateExpireYear() = expireYearState.apply {
            emptyValidation {
                val expiry = value
                val isError = expireYearRegex.matches(expiry)
                copy(
                    isError = isError,
                    error = UIText.StringResource(R.string.expire_year_must_be_in_form)
                )
            }
            _creditCardInfoState.updateExpireYear()
        }

        fun validateCCV() = ccvState.apply {
            emptyValidation {
                val ccv = value
                val isError = ccvRegex.matches(ccv)
                copy(
                    isError = isError,
                    error = UIText.StringResource(R.string.not_valid)
                )
            }
            _creditCardInfoState.updateCCV()
        }

        private fun TextFieldState.emptyValidation(then: () -> TextFieldState): TextFieldState {
            return if (value.isBlank())
                copy(isError = true, error = UIText.StringResource(R.string.required))
            else then()
        }
    }

    private fun MutableStateFlow<CreditCardInfoState>.updateFirstName() =
        update { it.copy(firstNameState = firstNameState) }

    private fun MutableStateFlow<CreditCardInfoState>.updateLastName() =
        update { it.copy(lastNameState = lastNameState) }

    private fun MutableStateFlow<CreditCardInfoState>.updateCardNumber() =
        update { it.copy(cardNumberState = cardNumberState) }

    private fun MutableStateFlow<CreditCardInfoState>.updateExpireMonth() =
        update { it.copy(expireMonthState = expireMonthState) }

    private fun MutableStateFlow<CreditCardInfoState>.updateExpireYear() =
        update { it.copy(expireYearState = expireYearState) }

    private fun MutableStateFlow<CreditCardInfoState>.updateCCV() =
        update { it.copy(ccvState = ccvState) }
}


