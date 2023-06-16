package com.example.shopify.ui.screen.order

import androidx.compose.runtime.Composable
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState

@Composable
fun OrdersScreen() {
    val state = OrderState()
    when (state.screenState) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> OrdersScreenContent(
            orders = state.orders,
            back = {},
            viewOrderDetails = {}
        )

        ScreenState.ERROR -> ErrorScreen {

        }
    }
}