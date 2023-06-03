package com.example.shopify.ui.screen.auth.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.helpers.Resource
import com.example.shopify.model.repository.ShopifyRepository
import com.example.shopify.ui.screen.auth.common.AuthUIEvent
import com.example.shopify.ui.screen.auth.common.ErrorAuthUiState
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserResponseInfo
import com.example.shopify.ui.screen.auth.login.ui.LoginUiState
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
    private val repository: ShopifyRepository
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiErrorState = MutableStateFlow(ErrorAuthUiState())
    val uiErrorState = _uiErrorState.asStateFlow()


    private val _uiEvent = MutableSharedFlow<AuthUIEvent<SignInUserResponseInfo>>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun signIn(){
        _uiErrorState.value = ErrorAuthUiState()
        repository.signIn(
            SignInUserInfo(_uiState.value.email.value,_uiState.value.password.value)
        ).onEach { response ->
            when (response) {
                is Resource.Error -> {
                    _uiErrorState.value = ErrorAuthUiState(response.throwable.message ?: "",true)
                }

                is Resource.Success -> {
                    if (response.data.error != null)
                        _uiErrorState.value = ErrorAuthUiState(response.data.error,true)
                    else
                        _uiEvent.emit(AuthUIEvent.NavigateToHome(response.data))
                }
            }
        }.launchIn(viewModelScope)
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

}