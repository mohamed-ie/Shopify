package com.example.shopify.ui.bottom_bar.my_account.change_phone_number

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIText
import com.example.shopify.helpers.validator.TextFieldStateValidator
import com.example.shopify.ui.bottom_bar.my_account.change_phone_number.view.ChangePhoneNumberEvent
import com.example.shopify.ui.bottom_bar.my_account.change_phone_number.view.ChangePhoneNumberState
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
class ChangePhoneNumberViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val inputValidator: TextFieldStateValidator
) : ViewModel() {

    private val _state = MutableStateFlow(ChangePhoneNumberState())
    val state = _state.asStateFlow()

    private val _back = MutableSharedFlow<Boolean>()
    val back = _back.asSharedFlow()


    fun onEvent(event: ChangePhoneNumberEvent) {
        when (event) {
            ChangePhoneNumberEvent.Change -> {
                val phoneState = inputValidator.validatePhone(state.value.phone)
                if (!phoneState.isError)
                    changePhoneNumber()
                else
                    _state.update { it.copy(remoteError = phoneState.error) }
            }


            is ChangePhoneNumberEvent.PhoneChanged ->
                updatePhoneValue(event.newValue)

        }
    }

    private fun changePhoneNumber() = viewModelScope.launch(defaultDispatcher) {
        when (val resource = repository.changePhoneNumber(state.value.phone.value)) {
            is Resource.Error ->
                _state.update { oldState ->
                    oldState.copy(
                        isLoading = true,
                        remoteError = UIText.StringResource(R.string.something_went_wrong)
                    )
                }

            is Resource.Success -> {
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
    }

    private fun updatePhoneValue(newValue: String) = _state.update { oldState ->
        oldState.copy(phone = oldState.phone.updateValue(newValue))
    }
}
