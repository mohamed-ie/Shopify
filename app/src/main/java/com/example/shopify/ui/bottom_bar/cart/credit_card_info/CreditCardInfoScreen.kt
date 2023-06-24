package com.example.shopify.ui.bottom_bar.cart.credit_card_info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.ui.common.screen.ErrorScreen
import com.example.shopify.ui.common.screen.LoadingScreen
import com.example.shopify.ui.common.state.ScreenState
import com.example.shopify.ui.Graph
import com.example.shopify.ui.bottom_bar.NavigationBarGraph
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CreditCardInfoScreen(
    viewModel: CreditCardInfoViewModel,
    back: () -> Unit,
    navigatePopUpTo: (route: String, pop: String) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.completed.onEach {
            navigatePopUpTo(Graph.ORDERS, NavigationBarGraph.CART)
        }.launchIn(this)
    }

    val state by viewModel.state.collectAsState()
    val screenState by viewModel.screenState.collectAsState()

    when (screenState) {
        ScreenState.LOADING ->
            LoadingScreen()

        ScreenState.STABLE ->
            CreditCardInfoScreenContent(
                state = state,
                onEvent = viewModel::onEvent,
                back = back
            )

        ScreenState.ERROR ->
            ErrorScreen { viewModel.onEvent(CreditCardInfoEvent.Checkout) }
    }

}