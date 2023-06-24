package com.example.shopify.ui.bottom_bar.my_account.my_account

import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.api_layer_exchange.repository.ApiLayerExchangeRepository
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.data.worker.UpdateCurrencyAmountWork
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.handle
import com.example.shopify.ui.bottom_bar.my_account.my_account.view.MyAccountEvent
import com.example.shopify.ui.bottom_bar.my_account.my_account.view.MyAccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val shopifyRepository: ShopifyRepository,
    private val workManager: WorkManager,
    private val currencyRepository: ApiLayerExchangeRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(MyAccountState())
    val state = _state.asStateFlow()

    val isSignedIn = shopifyRepository.isLoggedIn()

    init {
        loadCurrency()
    }

    private fun loadCurrency() {
        currencyRepository.currentCurrency.onEach { currency ->
            _state.update { it.copy(currency = currency) }
        }.launchIn(viewModelScope)
    }

    fun loadMinCustomerInfo() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        shopifyRepository.getMinCustomerInfo().handle(
            onError = ::toErrorScreenState,
            onSuccess = { data ->
                _state.update { it.copy(name = data.name, email = data.email) }
                toStableScreenState()
            }
        )
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

    private fun updateCurrency(newValue: String) = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        currencyRepository.changeCurrencyCode(newValue)
            .handle(
                onError = ::toErrorScreenState,
                onSuccess = {
                    _state.update { it.copy(isRadioGroupModalBottomSheetVisible = false) }
                    toStableScreenState()
                }
            )
        enqueueUpdateCurrencyWork()
    }

    private fun enqueueUpdateCurrencyWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequestBuilder<UpdateCurrencyAmountWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            UpdateCurrencyAmountWork.NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            work
        )
    }

    private fun signOut() = viewModelScope.launch {
        _state.update { it.copy(isSignOutDialogVisible = false) }
        shopifyRepository.signOut()
    }

}