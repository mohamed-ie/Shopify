package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment

sealed interface CreditCardInfoEvent {
    object Checkout: CreditCardInfoEvent

    class CardNumberChanged(val newValue: String) : CreditCardInfoEvent
    class ExpireMonthChanged(val newValue: String) : CreditCardInfoEvent
    class ExpireYearChanged(val newValue: String) : CreditCardInfoEvent
    class CCVChanged(val newValue: String) : CreditCardInfoEvent
    class FirstNameChanged(val newValue: String) : CreditCardInfoEvent
    class LastNameChanged(val newValue: String) : CreditCardInfoEvent
}
