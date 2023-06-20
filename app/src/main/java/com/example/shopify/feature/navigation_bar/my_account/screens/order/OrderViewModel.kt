package com.example.shopify.feature.navigation_bar.my_account.screens.order

import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.navigation_bar.my_account.screens.order.helpers.CreditCardInfoStateHandler
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment.CreditCardInfoEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.PaymentMethod
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view.CheckoutEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view.CheckoutState
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIText
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
    private val repository: ShopifyRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    val creditCardInfoState = creditCardInfoStateHandler.creditCardInfoState
    private val _uiEvent = MutableSharedFlow<OrderUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _checkoutState = MutableStateFlow(CheckoutState(Cart()))
    val checkoutState = _checkoutState.asStateFlow()

    private var _orderList = MutableStateFlow<List<Order>>(emptyList())
    val orderList = _orderList.asStateFlow()
    var orderIndex: Int = 0

    init {
        getOrders()
    }

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

            CheckoutEvent.PlaceOrder ->
                if (_checkoutState.value.cart.address.id == null)
                    _checkoutState.update {
                        it.copy(remoteError = UIText.StringResource(R.string.please_select_shipping_address))
                    }
                else {
                    _checkoutState.update { it.copy(remoteError = null) }
                    placeOrder()
                }

            CheckoutEvent.HideInvoiceDialog -> {
                _checkoutState.update { it.copy(isInvoiceDialogVisible = false) }
            }
        }
    }

    private fun handleCartResource(resource: Resource<Cart?>) =
        when (resource) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                if (resource.data != null)
                    _checkoutState.update {
                        it.copy(
                            cart = resource.data,
                            remoteError = resource.data.error?.let { it1 -> UIText.DynamicString(it1) }
                        )
                    }
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
                        remoteError = resource.data?.second?.let { it1 -> UIText.DynamicString(it1) },
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
                _checkoutState.update {
                    it.copy(remoteError = resource.data?.let { it1 ->
                        UIText.DynamicString(
                            it1
                        )
                    })
                }
                toStableScreenState()
            }
        }
    }

    private fun getOrders() {
        viewModelScope.launch(defaultDispatcher) {
            repository.getOrders().collect {
                when (it) {
                    is Resource.Success -> {
                        _orderList.emit(it.data)
                        toStableScreenState()
                    }

                    is Resource.Error -> toErrorScreenState()
                }
            }
        }
    }
}
