package com.example.shopify.feature.navigation_bar.my_account.screens.order

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.navigation_bar.my_account.screens.order.helpers.CreditCardInfoStateHandler
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment.CreditCardInfoEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.PaymentMethod
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view.CheckoutEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view.CheckoutState
import com.example.shopify.helpers.Resource
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
class OrderViewModel @Inject constructor(
    private val creditCardInfoStateHandler: CreditCardInfoStateHandler,
    private val defaultDispatcher: CoroutineDispatcher,
    private val repository: ShopifyRepository
) : BaseScreenViewModel() {
    val creditCardInfoState = creditCardInfoStateHandler.creditCardInfoState
    private val _uiEvent = MutableSharedFlow<OrderUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _checkoutState = MutableStateFlow(CheckoutState(Cart()))
    val checkoutState = _checkoutState.asStateFlow()

    fun loadOrderDetails() = viewModelScope.launch(defaultDispatcher) {
        handleCartResource(repository.getCart())
    }

    fun onCreditCardEvent(event: CreditCardInfoEvent) {
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

            CreditCardInfoEvent.Checkout -> {}
//                if (creditCardInfoStateHandler.isValid())
//                    processPayment()
        }
    }
    fun onCheckoutEvent(event: CheckoutEvent) {
        when (event) {
            is CheckoutEvent.PaymentMethodChanged ->
                _checkoutState.update { it.copy(selectedPaymentMethod = event.method) }

            CheckoutEvent.PlaceOrder -> placeOrder()
            CheckoutEvent.HideInvoiceDialog ->
                _checkoutState.update { it.copy(isInvoiceDialogVisible = false) }
        }
    }

    private fun handleCartResource(resource: Resource<Cart?>) =
        when (resource) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                if (resource.data != null)
                    _checkoutState.update { it.copy(cart = resource.data, remoteError = resource.data.error) }
                toStableScreenState()
            }
        }

    private fun placeOrder() {
        when (_checkoutState.value.selectedPaymentMethod) {
            PaymentMethod.CashOnDelivery -> processCashOnDelivery()
            PaymentMethod.CreditCard -> completePaymentWithCreditCard()
        }
    }

    private fun completePaymentWithCreditCard() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        when (val resource = repository.sendCompletePayment()) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                _checkoutState.update {
                    it.copy(
                        invoiceUrl = resource.data?.first,
                        remoteError = resource.data?.second,
                        isInvoiceDialogVisible = resource.data?.first != null
                    )
                }
                toStableScreenState()
            }
        }
    }

    private fun processCashOnDelivery() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        when (val resource = repository.completeOrder(paymentPending = true)) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                _checkoutState.update { it.copy(remoteError = resource.data) }
                toStableScreenState()
            }
        }
    }

}