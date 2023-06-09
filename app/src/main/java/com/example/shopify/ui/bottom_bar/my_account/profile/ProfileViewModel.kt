package com.example.shopify.ui.bottom_bar.my_account.profile

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.handle
import com.example.shopify.ui.bottom_bar.my_account.profile.view.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun loadMinCustomerInfo() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        repository.getMinCustomerInfo().handle(
            onError = ::toErrorScreenState,
            onSuccess = { data ->
                _state.update {
                    it.copy(
                        name = data.name,
                        email = data.email
                    )
                }
                toStableScreenState()
            }
        )
    }
}