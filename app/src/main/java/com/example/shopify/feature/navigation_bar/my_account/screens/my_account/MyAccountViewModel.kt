package com.example.shopify.feature.navigation_bar.my_account.screens.my_account

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view.MyAccountEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view.MyAccountState
import com.example.shopify.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val repository: ShopifyRepository,
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(MyAccountState())
    val state = _state.asStateFlow()

    val isSignedIn = repository.isLoggedIn()

    init {
        loadMinCustomerInfo()
    }

    private fun loadMinCustomerInfo() {
        repository.getMinCustomerInfo().onEach { resource ->
            when (resource) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            name = resource.data.name,
                            email = resource.data.email,
                            currency = resource.data.currency
                        )
                    }
                    toStableScreenState()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateCurrency(newValue: String) {
        _state.update { it.copy(isRadioGroupModalBottomSheetVisible = false, currency = newValue) }
        viewModelScope.launch { repository.updateCurrency(currency = newValue) }
    }

    fun onEvent(event: MyAccountEvent) {
        when (event) {
            is MyAccountEvent.CurrencyChanged ->
                updateCurrency(event.newValue)

            MyAccountEvent.SignOut ->
                signOut()

            MyAccountEvent.ToggleSignOutConfirmDialogVisibility ->
                _state.update { it.copy(isSignOutDialogVisible = it.isSignOutDialogVisible.not()) }

            MyAccountEvent.ToggleCurrencyRadioGroupModalSheetVisibility ->
                _state.update { it.copy(isRadioGroupModalBottomSheetVisible = it.isRadioGroupModalBottomSheetVisible.not()) }
        }
    }

    private fun signOut() {
        _state.update { it.copy(isSignOutDialogVisible = false) }
        viewModelScope.launch { repository.signOut() }
    }
}