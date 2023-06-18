package com.example.shopify.feature.auth.screens.registration.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.validator.auth.UserInputValidator
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.auth.screens.common.AuthUIEvent
import com.example.shopify.feature.auth.screens.common.ErrorAuthUiState
import com.example.shopify.feature.auth.screens.common.RegistrationUiState
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    private val userInputValidator: Lazy<UserInputValidator>
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AuthUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiErrorState = MutableStateFlow(ErrorAuthUiState())
    val uiErrorState = _uiErrorState.asStateFlow()

    private val _uiLoadingState = MutableStateFlow(false)
    val uiLoadingState = _uiLoadingState.asStateFlow()


    fun sendFirstNameValue(value:String){
        _uiState.update {registrationUiState ->
            registrationUiState.copy(firstName = registrationUiState.firstName.copy(value = value))
        }
    }
    fun sendSecondNameValue(value:String){
        _uiState.update {registrationUiState ->
            registrationUiState.copy(secondName = registrationUiState.secondName.copy(value = value))
        }
    }
    fun sendEmailValue(value:String){
        _uiState.update {registrationUiState ->
            registrationUiState.copy(email = registrationUiState.email.copy(value = value))
        }
    }
    fun sendPhoneValue(value:String){
        _uiState.update {registrationUiState ->
            registrationUiState.copy(phone = registrationUiState.phone.copy(value = value))
        }
    }
    fun sendPasswordValue(value:String){
        _uiState.update {registrationUiState ->
            registrationUiState.copy(password = registrationUiState.password.copy(value = value))
        }
    }


    fun signUp(){
        _uiErrorState.value = ErrorAuthUiState()
        _uiLoadingState.value = true
        updateFieldsErrors()
        if (isDataValid()) {
            repository.signUp(
                SignUpUserInfo(
                    _uiState.value.firstName.value,
                    _uiState.value.secondName.value,
                    _uiState.value.email.value,
                    _uiState.value.phone.value,
                    _uiState.value.password.value
                )
            ).onEach { response ->
                _uiLoadingState.value = false
                when (response) {
                    is Resource.Error -> {
                        _uiErrorState.value = ErrorAuthUiState(response.error.message ,true)
                    }

                    is Resource.Success -> {
                        if (response.data.error != null)
                            _uiErrorState.value = ErrorAuthUiState(response.data.error,true)
                        else{
                            loadCreatedEmail(repository.createUserEmail(_uiState.value.email.value))
                        }

                    }
                }
            }.launchIn(viewModelScope)
        }else {
            _uiLoadingState.value = false
        }
    }

    private fun loadCreatedEmail(response:Resource<Unit>){
        viewModelScope.launch {
            when(response){
                is Resource.Error -> {}
                is Resource.Success -> {
                    _uiEvent.emit(AuthUIEvent.NavigateToHome())
                }
            }
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
        _uiState.update {registrationUiState ->
            if (!userInputValidator.get().getValidPhone(registrationUiState.phone.value))
                registrationUiState.copy(phone = registrationUiState.phone.copy(error = R.string.phone_not_formed_error))
            else
                registrationUiState.copy(phone = registrationUiState.phone.copy(error = null))
        }
    }
    private fun updateFirstNameError() {
        _uiState.update {registrationUiState ->
            if (registrationUiState.firstName.value.isBlank())
                registrationUiState.copy(firstName = registrationUiState.firstName.copy(error = R.string.first_name_not_formed_error))
            else
                registrationUiState.copy(firstName = registrationUiState.firstName.copy(error = null))
        }
    }
    private fun updateSecondNameError() {
        _uiState.update {registrationUiState ->
            if (registrationUiState.secondName.value.isBlank())
                registrationUiState.copy(secondName = registrationUiState.secondName.copy(error = R.string.second_name_not_formed_error))
            else
                registrationUiState.copy(secondName = registrationUiState.secondName.copy(error = null))
        }
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
            if (!userInputValidator.get().getValidPassword( registrationUiState.password.value))
                registrationUiState.copy(password = registrationUiState.password.copy(error = R.string.password_not_formed_error))
            else
                registrationUiState.copy(password = registrationUiState.password.copy(error = null))
        }
    }
    private fun isDataValid(): Boolean =
         _uiState.value.firstName.error == null
                &&  _uiState.value.secondName.error == null
                &&  _uiState.value.email.error == null
                &&  _uiState.value.phone.error == null
                &&  _uiState.value.password.error == null


}