package com.example.shopify.ui.screen.auth.registration.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.helpers.Resource
import com.example.shopify.model.repository.ShopifyRepository
import com.example.shopify.ui.screen.auth.common.AuthUIEvent
import com.example.shopify.ui.screen.auth.helper.UserInputValidator
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    private val userInputValidator: UserInputValidator
) : ViewModel() {

    val firstName = MutableStateFlow("")
    private val _firstNameError = MutableStateFlow<Int?>(null)
    val firstNameError = _firstNameError.asStateFlow()

    val secondName = MutableStateFlow("")
    private val _secondNameError = MutableStateFlow<Int?>(null)
    val secondNameError = _secondNameError.asStateFlow()

    val email = MutableStateFlow("")
    private val _emailError = MutableStateFlow<Int?>(null)
    val emailError = _emailError.asStateFlow()

    val phone = MutableStateFlow("")
    private val _phoneError = MutableStateFlow<Int?>(null)
    val phoneError = _phoneError.asStateFlow()


    val password = MutableStateFlow("")
    private val _passwordError = MutableStateFlow<Int?>(null)
    val passwordError = _passwordError.asStateFlow()


    private val _uiEvent = MutableStateFlow<AuthUIEvent<SignUpUserResponseInfo>?>(null)
    val uiEvent = _uiEvent.asStateFlow()


    fun signUp(){
        updateFieldsErrors()
        if (isDataValid()) {
            _uiEvent.value = AuthUIEvent.Loading
            repository.signUp(
                SignUpUserInfo(
                    firstName.value,
                    secondName.value,
                    email.value,
                    phone.value,
                    password.value
                )
            ).onEach { response ->
                when (response) {
                    is Resource.Error -> {
                        _uiEvent.emit(AuthUIEvent.Error(response.throwable.message ?: ""))
                    }

                    is Resource.Success -> {
                        if (response.data.error != null)
                            _uiEvent.emit(AuthUIEvent.Error(response.data.error))
                        else
                            _uiEvent.emit(AuthUIEvent.NavigateToHome(response.data))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    private fun updateFieldsErrors() {
        updateFirstNameError()
        updateSecondNameError()
        updateEmailError()
        updatePhoneError()
        updatePasswordError()
    }

    private fun updatePhoneError() {
        _phoneError.update {
            if (!userInputValidator.getValidPhone(phone.value))
                R.string.phone_not_formed_error
            else
                null
        }
    }

    private fun updateFirstNameError() {
        _firstNameError.update {
            if (firstName.value.isBlank())
                R.string.first_name_not_formed_error
            else
                null
        }
    }

    private fun updateSecondNameError() {
        _secondNameError.update {
            if (secondName.value.isBlank())
                R.string.second_name_not_formed_error
            else
               null
        }
    }

    private fun updateEmailError() {
        _emailError.update {
            if (!userInputValidator.getValidEmail(email.value))
                R.string.email_not_formed_error
            else
                null
        }
    }

    private fun updatePasswordError() {
        _passwordError.update {
            if (!userInputValidator.getValidPassword(password.value))
                R.string.password_not_formed_error
            else
                null
        }
    }

     private fun isDataValid(): Boolean =
        firstNameError.value == null
                && secondNameError.value == null
                && phoneError.value == null
                && emailError.value == null
                && passwordError.value == null


}