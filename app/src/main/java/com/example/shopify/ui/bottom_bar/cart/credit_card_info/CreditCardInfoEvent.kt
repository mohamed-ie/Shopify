package com.example.shopify.ui.bottom_bar.cart.credit_card_info

sealed interface CreditCardInfoEvent {
    object Checkout: CreditCardInfoEvent

    class CardNumberChanged(val newValue: String) : CreditCardInfoEvent
    class ExpireMonthChanged(val newValue: String) : CreditCardInfoEvent
    class ExpireYearChanged(val newValue: String) : CreditCardInfoEvent
    class CCVChanged(val newValue: String) : CreditCardInfoEvent
    class FirstNameChanged(val newValue: String) : CreditCardInfoEvent
    class LastNameChanged(val newValue: String) : CreditCardInfoEvent
}
