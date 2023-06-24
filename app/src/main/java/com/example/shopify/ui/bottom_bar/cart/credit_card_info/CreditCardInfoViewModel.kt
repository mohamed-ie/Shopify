package com.example.shopify.ui.bottom_bar.cart.credit_card_info

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIText
import com.example.shopify.helpers.cart.CreditCardInfoStateHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCardInfoViewModel @Inject constructor(
    private val creditCardInfoStateHandler: CreditCardInfoStateHandler,
    private val repository: ShopifyRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    val state = creditCardInfoStateHandler.creditCardInfoState

    private val _completed = MutableSharedFlow<Unit>()
    val completed = _completed.asSharedFlow()

    init {
        toStableScreenState()
    }

    fun onEvent(event: CreditCardInfoEvent) {
        when (event) {
            is CreditCardInfoEvent.FirstNameChanged ->
                creditCardInfoStateHandler.updateFirstName(event.newValue)

            is CreditCardInfoEvent.LastNameChanged ->
                creditCardInfoStateHandler.updateLastName(event.newValue)

            is CreditCardInfoEvent.CardNumberChanged ->
                creditCardInfoStateHandler.updateCardNumber(event.newValue)

            is CreditCardInfoEvent.ExpireMonthChanged ->
                creditCardInfoStateHandler.updateExpireMonth(event.newValue)

            is CreditCardInfoEvent.ExpireYearChanged ->
                creditCardInfoStateHandler.updateExpireYear(event.newValue)

            is CreditCardInfoEvent.CCVChanged ->
                creditCardInfoStateHandler.updateCCV(event.newValue)

            CreditCardInfoEvent.Checkout ->
                if (creditCardInfoStateHandler.isValid())
                    processCreditCardPayment()
        }
    }
    
    private fun processCreditCardPayment() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        when (val resource = repository.completeOrder(paymentPending = false)) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                creditCardInfoStateHandler.updateRemoteError(resource.data?.let {
                    UIText.DynamicString(
                        it
                    )
                })
                _completed.emit(Unit)
                toStableScreenState()
            }
        }
    }
}