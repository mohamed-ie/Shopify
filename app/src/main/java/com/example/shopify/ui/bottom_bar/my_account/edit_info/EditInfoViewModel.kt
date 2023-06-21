package com.example.shopify.ui.bottom_bar.my_account.edit_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIText
import com.example.shopify.ui.bottom_bar.my_account.edit_info.view.EditInfoEvent
import com.example.shopify.ui.bottom_bar.my_account.edit_info.view.EditInfoState
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
class EditInfoViewModel @Inject constructor(
    val repository: ShopifyRepository,
    @DefaultDispatcher val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(EditInfoState())
    val state = _state.asStateFlow()

    private val _back = MutableSharedFlow<Boolean>()
    val back = _back.asSharedFlow()


    fun onEvent(event: EditInfoEvent) {
        when (event) {
            is EditInfoEvent.FirstNameChanged ->
                updateFirstNameValue(event.newValue)

            is EditInfoEvent.LastNameChanged ->
            updateLastNameValue(event.newValue)


            EditInfoEvent.Change ->   _state.value.run {
                _state.update { it.copy(remoteError = null) }
                if (firstName.value.isNotBlank() == lastName.value.isNotBlank())
                    changePassword()
                else
                    _state.update { it.copy(remoteError = UIText.StringResource(R.string.passwords_dont_match)) }
            }
        }
    }

    private fun changePassword() = viewModelScope.launch(defaultDispatcher) {
        _state.update { it.copy(isLoading = true) }
        val firstName = _state.value.firstName.value
        val lastName = _state.value.lastName.value
        when (val resource = repository.changeName(firstName, lastName)) {
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

    private fun updateFirstNameValue(newValue: String) = _state.update { oldState ->
        oldState.copy(firstName = oldState.firstName.updateValue(newValue))
    }

    private fun updateLastNameValue(newValue: String) = _state.update { oldState ->
        oldState.copy(lastName = oldState.lastName.updateValue(newValue))
    }

}
