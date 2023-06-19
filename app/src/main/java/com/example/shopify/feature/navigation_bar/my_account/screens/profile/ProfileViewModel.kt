package com.example.shopify.feature.navigation_bar.my_account.screens.profile

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ShopifyRepository
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()


     fun loadMinCustomerInfo() {
        toLoadingScreenState()
        repository.getMinCustomerInfo().onEach { resource ->
            when (resource) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            name = resource.data.name,
                            email = resource.data.email
                        )
                    }
                    toStableScreenState()
                }
            }
        }.launchIn(viewModelScope)
    }

}