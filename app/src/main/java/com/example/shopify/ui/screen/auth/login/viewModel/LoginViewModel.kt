package com.example.shopify.ui.screen.auth.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.helpers.Resource
import com.example.shopify.model.repository.ShopifyRepository
import com.example.shopify.ui.screen.auth.common.AuthUIEvent
import com.example.shopify.ui.screen.auth.helper.UserInputValidator
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserResponseInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    private val userInputValidator: UserInputValidator
) : ViewModel() {

    val email = MutableStateFlow("")
    private val _emailError = MutableStateFlow<Int?>(null)
    val emailError = _emailError.asStateFlow()

    val password = MutableStateFlow("")
    private val _passwordError = MutableStateFlow<Int?>(null)
    val passwordError = _passwordError.asStateFlow()


    private val _uiEvent = MutableStateFlow<AuthUIEvent<SignInUserResponseInfo>?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    fun signIn(){
        updateFieldsErrors()
        if (isDataValid()) {
            _uiEvent.value = AuthUIEvent.Loading
            repository.signIn(
                SignInUserInfo(email.value,password.value)
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
        updateEmailError()
        updatePasswordError()
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
        emailError.value == null
                && passwordError.value == null

}