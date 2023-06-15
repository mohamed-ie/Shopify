package com.example.shopify.feature.auth.screens.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.validator.auth.UserInputValidator
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.auth.screens.common.AuthUIEvent
import com.example.shopify.feature.auth.screens.common.ErrorAuthUiState
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.ui.LoginUiState
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    private val userInputValidator: Lazy<UserInputValidator>
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiErrorState = MutableStateFlow(ErrorAuthUiState())
    val uiErrorState = _uiErrorState.asStateFlow()


    private val _uiEvent = MutableSharedFlow<AuthUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiLoadingState = MutableStateFlow(false)
    val uiLoadingState = _uiLoadingState.asStateFlow()

    fun signIn(){
        _uiErrorState.value = ErrorAuthUiState()
        _uiLoadingState.value = true
        updateFieldsErrors()
        if (isDataValid()) {
            repository.signIn(
                SignInUserInfo(_uiState.value.email.value, _uiState.value.password.value)
            ).onEach { response ->
                _uiLoadingState.value = false
                when (response) {
                    is Resource.Error -> {
                        _uiErrorState.value =
                            ErrorAuthUiState(response.error.message, true)
                    }

                    is Resource.Success -> {
                        if (response.data.error.isNotEmpty())
                            _uiErrorState.value = ErrorAuthUiState(response.data.error, true)
                        else {
                            repository.saveUserInfo(response.data)
                            _uiEvent.emit(AuthUIEvent.NavigateToHome())
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }else{
            _uiLoadingState.value = false
        }
    }


    fun sendEmailValue(value:String){
        _uiState.update {registrationUiState ->
            registrationUiState.copy(email = registrationUiState.email.copy(value = value))
        }
    }

    fun sendPasswordValue(value:String){
        _uiState.update {registrationUiState ->
            registrationUiState.copy(password = registrationUiState.password.copy(value = value))
        }
    }

    private fun updateFieldsErrors() {
        updateEmailError()
        updatePasswordError()
    }


    private fun updateEmailError() {
        _uiState.update {registrationUiState ->
            if (!userInputValidator.get().getValidEmail( registrationUiState.email.value))
                registrationUiState.copy(email = registrationUiState.email.copy(error = R.string.email_not_formed_error))
            else
                registrationUiState.copy(email = registrationUiState.email.copy(error = null))
        }
    }
    private fun updatePasswordError() {
        _uiState.update {registrationUiState ->
            if (registrationUiState.password.value.isEmpty() || registrationUiState.password.value.isBlank() )
                registrationUiState.copy(password = registrationUiState.password.copy(error = R.string.error_empty_password_field))
            else
                registrationUiState.copy(password = registrationUiState.password.copy(error = null))
        }
    }

    private fun isDataValid(): Boolean =
        _uiState.value.email.error == null
                && _uiState.value.password.error == null


}