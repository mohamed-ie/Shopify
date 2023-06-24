package com.example.shopify.ui.bottom_bar.cart.checkout.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.R
import com.example.shopify.ui.Graph
import com.example.shopify.ui.bottom_bar.NavigationBarGraph
import com.example.shopify.ui.bottom_bar.cart.CartGraph
import com.example.shopify.ui.bottom_bar.cart.checkout.CheckoutViewModel
import com.example.shopify.ui.bottom_bar.cart.checkout.view.event.CheckoutEvent
import com.example.shopify.ui.bottom_bar.cart.checkout.view.event.CheckoutUIEvent
import com.example.shopify.ui.common.ConfirmationDialog
import com.example.shopify.ui.common.screen.ErrorScreen
import com.example.shopify.ui.common.screen.LoadingScreen
import com.example.shopify.ui.common.state.ScreenState
import com.stripe.android.PaymentConfiguration
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.paymentsheet.PaymentSheetContract
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
    val state by viewModel.state.collectAsState()
    val stripeLauncher = rememberLauncherForActivityResult(
        contract = PaymentSheetContract(),
        onResult = { viewModel.onEvent(CheckoutEvent.HandlePaymentResult(it)) }
    )
    state.clientSecret?.let {
        val args = PaymentSheetContract.Args.createPaymentIntentArgs(it)
        stripeLauncher.launch(args)
        viewModel.onEvent(CheckoutEvent.ClearClientSecret)
    }

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
                CheckoutUIEvent.NavigateToCreditCardInfoScreen ->
                    navigatePopUpTo(CartGraph.CREDIT_CARD_INFO, null)

                CheckoutUIEvent.NavigateToOrdersScreen ->
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
            onEvent = viewModel::onEvent
        )

        ScreenState.ERROR -> ErrorScreen { viewModel.loadOrderDetails() }
    }

    ConfirmationDialog(
        visible = state.isInvoiceDialogVisible,
        message = stringResource(id = R.string.complete_your_purchase),
        onDismiss = { viewModel.onEvent(CheckoutEvent.HideInvoiceDialog) },
        onConfirm = { uriHandler.openUri(state.invoiceUrl!!) })
}

@Composable
private fun rememberPaymentLauncher(
    callback: PaymentLauncher.PaymentResultCallback
): PaymentLauncher {
    val config = PaymentConfiguration.getInstance(LocalContext.current)
    return PaymentLauncher.rememberLauncher(
        publishableKey = config.publishableKey,
        stripeAccountId = config.stripeAccountId,
        callback = callback
    )
}