package com.example.shopify.feature.address.addresses

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.address.addresses.view.AddressesEvent
import com.example.shopify.feature.address.addresses.view.AddressesState
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import com.shopify.graphql.support.ID
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
    val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(AddressesState())
    val state = _state.asStateFlow()

    private val _back = MutableSharedFlow<Boolean>()
    val back = _back.asSharedFlow()

    private var addressId: ID? = null

    fun loadAddresses() {
        viewModelScope.launch {
            when (val resource = repository.getAddresses()) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    _state.update { it.copy(addresses = resource.data) }
                    toStableScreenState()
                }
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
                addressId = event.id
                _state.update { it.copy(isDeleteDialogVisible = it.isDeleteDialogVisible.not()) }
            }

            is AddressesEvent.UpdateCartAddress ->
                updateCartAddress(event.id)
        }
    }

    private fun updateCartAddress(id: ID) = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        when (repository.updateCartAddress(id)) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success ->  _back.emit(true)
        }
    }

    private fun deleteAddress() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        addressId?.let {
            when (repository.deleteAddress(it)) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    toStableScreenState()
                    loadAddresses()
                }
            }
        }
    }

}