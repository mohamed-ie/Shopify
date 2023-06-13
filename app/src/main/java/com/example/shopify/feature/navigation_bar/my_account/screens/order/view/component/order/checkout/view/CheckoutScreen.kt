package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.cart.CartGraph
import com.example.shopify.feature.navigation_bar.my_account.screens.order.OrderViewModel
import com.example.shopify.theme.ShopifyTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CheckoutScreen(
    viewModel: OrderViewModel,
    navigateTo: (String) -> Unit,
    back: () -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.completeCheckOut.onEach {
            navigateTo(CartGraph.CREDIT_CARD)
        }.launchIn(this)
    })
    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> CheckoutContent(
            cart = viewModel.cart,
            navigateTo = navigateTo,
            back = back,
            onPlaceOrder = viewModel::getCheckOutID
        )

        ScreenState.ERROR -> {}
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCheckOutScreen() {
    ShopifyTheme {
        CheckoutScreen(hiltViewModel(), {}, {})
    }
}