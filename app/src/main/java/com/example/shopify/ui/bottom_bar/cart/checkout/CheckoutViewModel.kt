package com.example.shopify.ui.bottom_bar.cart.checkout

import androidx.lifecycle.viewModelScope
import com.example.shopify.R
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.data.stripe.repository.StripeRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIText
import com.example.shopify.helpers.getOrNull
import com.example.shopify.model.cart.cart.Cart
import com.example.shopify.ui.bottom_bar.cart.checkout.view.event.CheckoutEvent
import com.example.shopify.ui.bottom_bar.cart.checkout.view.event.CheckoutUIEvent
import com.example.shopify.ui.bottom_bar.cart.checkout.view.state.CheckoutState
import com.example.shopify.ui.bottom_bar.cart.checkout.view.state.PaymentMethod
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val shopifyRepository: ShopifyRepository,
    private val stripeRepository: StripeRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    private val _uiEvent = MutableSharedFlow<CheckoutUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _state = MutableStateFlow(CheckoutState(Cart()))
    val state = _state.asStateFlow()

    fun loadOrderDetails() = viewModelScope.launch(defaultDispatcher) {
        handleCartResource(shopifyRepository.getCart())
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

            CheckoutEvent.ClearClientSecret ->
                _state.update { it.copy(clientSecret = null) }

            is CheckoutEvent.HandlePaymentResult ->
                handlePaymentResult(event.result)
        }
    }

    private fun handlePaymentResult(result: PaymentSheetResult) {
        when (result) {
            PaymentSheetResult.Canceled -> {}
            PaymentSheetResult.Completed -> {
                completePayment(false)
            }

            is PaymentSheetResult.Failed -> toErrorScreenState()
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
            PaymentMethod.CashOnDelivery -> completePayment()
            PaymentMethod.CreditCard -> processStripePayment()
            PaymentMethod.Shopify -> completePaymentWithShopify()
        }
    }

    private fun processStripePayment() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()

        val customerId =
            stripeRepository.createCustomer().getOrNull() ?: return@launch toErrorScreenState()
        val ephemeralKey = stripeRepository.createEphemeralKey(customerId).getOrNull()
            ?: return@launch toErrorScreenState()
        val amount =
            state.value.cart.totalPrice.toFloatOrNull() ?: return@launch toErrorScreenState()

        val currency = state.value.cart.currencyCode

        val clientSecret = stripeRepository.createPaymentIntent(
            customerId = customerId,
            amount = amount.times(100).toInt(),
            currency = currency,
            autoPaymentMethodsEnable = true
        ).getOrNull() ?: return@launch toErrorScreenState()
        toLoadingScreenState()
        delay(500)
        _state.update { it.copy(clientSecret = clientSecret) }

    }

    private fun completePaymentWithShopify() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        when (val resource = shopifyRepository.sendCompletePayment()) {
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

    private fun completePayment(paymentPending: Boolean = true) =
        viewModelScope.launch(defaultDispatcher) {
            toLoadingScreenState()
            when (val resource = shopifyRepository.completeOrder(paymentPending = paymentPending)) {
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
