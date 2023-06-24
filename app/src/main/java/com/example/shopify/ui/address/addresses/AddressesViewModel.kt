package com.example.shopify.ui.address.addresses

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.ui.address.addresses.view.AddressesEvent
import com.example.shopify.ui.address.addresses.view.AddressesState
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
class AddressesViewModel @Inject constructor(
    val repository: ShopifyRepository,
    @DefaultDispatcher val defaultDispatcher: CoroutineDispatcher,
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(AddressesState())
    val state = _state.asStateFlow()

    private val _back = MutableSharedFlow<Boolean>()
    val back = _back.asSharedFlow()

    private var addressIndex:Int ? = null

    fun loadAddresses() = viewModelScope.launch {
            when (val resource = repository.getAddresses()) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    _state.update { it.copy(addresses = resource.data) }
                    toStableScreenState()
                }
            }
        }


    fun onEvent(event: AddressesEvent) {
        when (event) {
            AddressesEvent.DeleteAddress -> {
                _state.update { it.copy(isDeleteDialogVisible = it.isDeleteDialogVisible.not()) }
                deleteAddress()
            }

            is AddressesEvent.ToggleDeleteConfirmationDialogVisibility -> {
                addressIndex = event.index
                _state.update { it.copy(isDeleteDialogVisible = it.isDeleteDialogVisible.not()) }
            }

            is AddressesEvent.UpdateAddress ->
                updateShippingAddress(event.index)
        }
    }

    private fun updateShippingAddress(index:Int) = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        when (repository.updateCartShippingAddress(state.value.addresses[index])) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success ->  _back.emit(true)
        }
    }

    private fun deleteAddress() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        addressIndex?.let {
            when (repository.deleteAddress(state.value.addresses[it].id)) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    toStableScreenState()
                    loadAddresses()
                }
            }
        }
    }

}