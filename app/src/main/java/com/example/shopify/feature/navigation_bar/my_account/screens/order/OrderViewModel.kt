package com.example.shopify.feature.navigation_bar.my_account.screens.order

import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.my_account.screens.order.helpers.CreditCardInfoStateHandler
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment.PaymentStrategy
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment.ShopifyCreditCardPaymentStrategy
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment.CreditCardInfoEvent
import com.example.shopify.helpers.Resource
import com.shopify.buy3.CreditCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val creditCardInfoStateHandler: CreditCardInfoStateHandler,
    private val creditCardPaymentStrategy: PaymentStrategy<ShopifyCreditCardPaymentStrategy.ShopifyPaymentInfo, Flow<Resource<ShopifyCreditCardPaymentStrategy.PaymentResult>>>
) : BaseScreenViewModel() {
    val creditCardInfoState = creditCardInfoStateHandler.creditCardInfoState

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

            CreditCardInfoEvent.Checkout ->
                if (creditCardInfoStateHandler.isValid())
                    processPayment()
        }
    }

    private fun processPayment() {
//        val checkoutId: ID =
//        val amount: String =
//        val currency: CurrencyCode =
//
        val cardValues = creditCardInfoState.value

        val creditCard: CreditCard = CreditCard(
            number = cardValues.cardNumberState.value,
            firstName = cardValues.firstNameState.value,
            lastName = cardValues.lastNameState.value,
            expireMonth = cardValues.expireMonthState.value,
            expireYear = cardValues.expireYearState.value,
            verificationCode = cardValues.ccvState.value
        )

//        val billingAddress: MailingAddressInput = MailingAddressInput().apply {
//            this.address1 =  //street
//            this.address2 = //apartmentNumber
//            this.province = //state
//            this.city = city
//            this.country = country
//            this.firstName = firstName
//            this.lastName = lastName
//            this.phone = phone
//            this.zip = zip
//            this.company = company
//        }

//        val paymentInfo = ShopifyCreditCardPaymentStrategy.ShopifyPaymentInfo(
//            checkoutId = checkoutId,
//            amount = amount,
//            currency = currency,
//            creditCard = creditCard,
//            billingAddress = billingAddress,
//        )

//        creditCardPaymentStrategy.pay(paymentInfo).onEach { resource ->
//            when (resource){
//                is Resource.Error -> {}
//                is Resource.Success -> {
//                    resource.data.orderId
//                }
//            }
//        }
    }
}