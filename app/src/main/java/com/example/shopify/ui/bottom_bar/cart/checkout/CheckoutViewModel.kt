package com.example.shopify.ui.bottom_bar.cart.checkout

import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIText
import com.example.shopify.model.cart.cart.Cart
import com.example.shopify.ui.bottom_bar.cart.checkout.view.event.CheckoutEvent
import com.example.shopify.ui.bottom_bar.cart.checkout.view.event.CheckoutUIEvent
import com.example.shopify.ui.bottom_bar.cart.checkout.view.state.CheckoutState
import com.example.shopify.ui.bottom_bar.cart.checkout.view.state.PaymentMethod
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
class CheckoutViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    private val _uiEvent = MutableSharedFlow<CheckoutUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _state = MutableStateFlow(CheckoutState(Cart()))
    val state = _state.asStateFlow()

    fun loadOrderDetails() = viewModelScope.launch(defaultDispatcher) {
        handleCartResource(repository.getCart())
    }

    fun onEvent(event: CheckoutEvent) {
        when (event) {
            is CheckoutEvent.PaymentMethodChanged ->
                _state.update { it.copy(selectedPaymentMethod = event.method) }

            CheckoutEvent.PlaceOrder ->
                if (_state.value.cart.address.id == null)
                    _state.update {
                        it.copy(remoteError = UIText.StringResource(R.string.please_select_shipping_address))
                    }
                else {
                    _state.update { it.copy(remoteError = null) }
                    placeOrder()
                }

            CheckoutEvent.HideInvoiceDialog -> {
                _state.update { it.copy(isInvoiceDialogVisible = false) }
            }
        }
    }

    private fun handleCartResource(resource: Resource<Cart?>) =
        when (resource) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                if (resource.data != null)
                    _state.update {
                        it.copy(
                            cart = resource.data,
                            remoteError = resource.data.error?.let { it1 -> UIText.DynamicString(it1) }
                        )
                    }
                toStableScreenState()
            }
        }

    private fun placeOrder() = viewModelScope.launch {
        when (_state.value.selectedPaymentMethod) {
            PaymentMethod.CashOnDelivery -> processCashOnDelivery()
            PaymentMethod.CreditCard -> _uiEvent.emit(CheckoutUIEvent.NavigateToCreditCardInfoScreen)
            PaymentMethod.Shopify -> completePaymentWithShopify()
        }
    }

    private fun completePaymentWithShopify() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        when (val resource = repository.sendCompletePayment()) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                _state.update {
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
                if (resource.data == null)
                    _uiEvent.emit(CheckoutUIEvent.NavigateToOrdersScreen)
                else {
                    _state.update { it.copy(remoteError = UIText.DynamicString(resource.data)) }
                    toStableScreenState()
                }
            }
        }
    }

}
