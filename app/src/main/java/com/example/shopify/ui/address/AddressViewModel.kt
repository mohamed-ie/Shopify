package com.example.shopify.ui.address

import androidx.lifecycle.ViewModel
import com.shopify.buy3.Storefront.MailingAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor() :ViewModel(){
    private val _address = MutableStateFlow<MailingAddress?>(null)
    val address = _address.asStateFlow()

    fun setAddress(address: MailingAddress) {
        _address.update { address }
    }

    fun clearAddress() {
        _address.update { null }
    }
}