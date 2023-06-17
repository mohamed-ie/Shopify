package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.my_account.screens.order.OrderViewModel
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.OrderGraph
import com.example.shopify.ui.screen.order.OrdersScreenContent

@Composable
fun OrdersScreen(
    viewModel: OrderViewModel,
    navigateTo: (String) -> Unit,
    back: () -> Unit
) {
    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> OrdersScreenContent(
            orders = viewModel.orderList.collectAsState().value,
            back = back,
            viewOrderDetails = {
                viewModel.orderIndex = it
                navigateTo(OrderGraph.ORDER_DETAILS)
            },

            )

        ScreenState.ERROR -> ErrorScreen {

        }
    }
}