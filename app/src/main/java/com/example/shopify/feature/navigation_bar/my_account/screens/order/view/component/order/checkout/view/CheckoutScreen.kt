package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.R
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.cart.CartGraph
import com.example.shopify.feature.navigation_bar.common.ConfirmationDialog
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.my_account.screens.order.OrderUIEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.order.CheckoutViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CheckoutScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: CheckoutViewModel,
    navigatePopUpTo: (route: String, pop: String?) -> Unit,
    back: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val state by viewModel.checkoutState.collectAsState()

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadOrderDetails()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.uiEvent.onEach {
            when (it) {
                OrderUIEvent.NavigateToCreditCardInfoScreen ->
                    navigatePopUpTo(CartGraph.CREDIT_CARD_INFO, null)

                OrderUIEvent.NavigateToOrdersScreen ->
                    navigatePopUpTo(Graph.ORDERS, NavigationBarGraph.CART)
            }
        }.launchIn(this)
    })

    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> CheckoutContent(
            state = state,
            navigateTo = { navigatePopUpTo(it, null) },
            back = back,
            onEvent = viewModel::onCheckoutEvent
        )

        ScreenState.ERROR -> ErrorScreen { viewModel.loadOrderDetails() }
    }

    ConfirmationDialog(
        visible = state.isInvoiceDialogVisible,
        message = stringResource(id = R.string.complete_your_purchase),
        onDismiss = { viewModel.onCheckoutEvent(CheckoutEvent.HideInvoiceDialog) },
        onConfirm = { uriHandler.openUri(state.invoiceUrl!!) })
}
