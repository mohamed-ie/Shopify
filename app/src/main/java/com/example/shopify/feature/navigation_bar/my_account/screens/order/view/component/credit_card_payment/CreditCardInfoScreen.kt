package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.my_account.screens.order.CheckoutViewModel
import com.example.shopify.feature.navigation_bar.my_account.screens.order.OrderUIEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CreditCardInfoScreen(viewModel: CheckoutViewModel, back: () -> Unit, navigatePopUpTo:(route:String, pop:String)->Unit) {
    val state by viewModel.creditCardInfoState.collectAsState()
    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> CreditCardInfoScreenContent(
            state = state,
            onEvent = viewModel::onCreditCardEvent,
            back = back
        )
        ScreenState.ERROR -> ErrorScreen { viewModel.onCreditCardEvent(CreditCardInfoEvent.Checkout) }
    }
    LaunchedEffect(key1 = Unit){
        viewModel.uiEvent.onEach {
            if (it is OrderUIEvent.NavigateToOrdersScreen){
                navigatePopUpTo(Graph.ORDERS, NavigationBarGraph.CART)
            }
        }.launchIn(this)
    }
}