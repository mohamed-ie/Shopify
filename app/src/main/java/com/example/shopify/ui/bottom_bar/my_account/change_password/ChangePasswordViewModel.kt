package com.example.shopify.ui.bottom_bar.my_account.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIText
import com.example.shopify.ui.bottom_bar.my_account.change_password.view.ChangePasswordEvent
import com.example.shopify.ui.bottom_bar.my_account.change_password.view.ChangePasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordState())
    val state = _state.asStateFlow()

    private val _back = MutableSharedFlow<Boolean>()
    val back = _back.asSharedFlow()

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.ConfirmPasswordChanged ->
                updateConfirmPasswordValue(event.newValue)

            is ChangePasswordEvent.PasswordChanged ->
                updatePasswordValue(event.newValue)

            ChangePasswordEvent.ToggleConfirmPasswordVisibility ->
                toggleConfirmPasswordVisibility()

            ChangePasswordEvent.TogglePasswordVisibility ->
                togglePasswordVisibility()

            ChangePasswordEvent.Change ->
                _state.value.run {
                    _state.update { it.copy(remoteError = null) }
                    if (confirmPassword.value == password.value && password.value.isNotBlank())
                        changePassword()
                    else
                        _state.update { it.copy(remoteError = UIText.StringResource(R.string.passwords_dont_match)) }
                }
        }
    }

    private fun changePassword() = viewModelScope.launch(defaultDispatcher) {
        _state.update { it.copy(isLoading = true) }
        when (val resource = repository.changePassword(state.value.password.value)) {
            is Resource.Error ->
                _state.update { oldState ->
                    oldState.copy(
                        isLoading = true,
                        remoteError = UIText.StringResource(R.string.something_went_wrong)
                    )
                }

            is Resource.Success ->
                if (resource.data == null)
                    _back.emit(true)
                else
                    _state.update { oldState ->
                        oldState.copy(
                            remoteError = UIText.DynamicString(resource.data),
                            isLoading = false
                        )
                    }
        }
    }

    private fun updateConfirmPasswordValue(newValue: String) = _state.update { oldState ->
        oldState.copy(confirmPassword = oldState.confirmPassword.updateValue(newValue))
    }

    private fun updatePasswordValue(newValue: String) = _state.update { oldState ->
        oldState.copy(password = oldState.password.updateValue(newValue))
    }

    private fun toggleConfirmPasswordVisibility() = _state.update { oldState ->
        val triallingIconState = oldState.confirmPassword.triallingIconState.not()
        val confirmPasswordState =
            oldState.confirmPassword.copy(triallingIconState = triallingIconState)
        oldState.copy(confirmPassword = confirmPasswordState)
    }

    private fun togglePasswordVisibility() = _state.update { oldState ->
        val triallingIconState = oldState.password.triallingIconState.not()
        val passwordState = oldState.password.copy(triallingIconState = triallingIconState)
        oldState.copy(password = passwordState)
    }

}



