package com.example.shopify.feature.navigation_bar.my_account.screens.order

sealed interface OrderUIEvent {
    object NavigateToOrdersScreen : OrderUIEvent
    object NavigateToCreditCardInfoScreen : OrderUIEvent
}
