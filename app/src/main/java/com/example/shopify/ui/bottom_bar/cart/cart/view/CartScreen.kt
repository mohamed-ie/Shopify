package com.example.shopify.ui.bottom_bar.cart.cart.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.ui.common.screen.ErrorScreen
import com.example.shopify.ui.common.screen.LoadingScreen
import com.example.shopify.ui.common.state.ScreenState
import com.example.shopify.ui.bottom_bar.cart.cart.CartViewModel

@Composable
fun CartScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: CartViewModel,
    navigateTo: (String) -> Unit
) {
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadCart()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val screenState by viewModel.screenState.collectAsState()
    val state by viewModel.state.collectAsState()
    val itemsState by viewModel.cartLinesState.collectAsState()
    val couponState by viewModel.couponState.collectAsState()

    when (screenState) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> CartScreenContent(
            state = state,
            itemsState = itemsState,
            couponState = couponState,
            onCartItemEvent = viewModel::onCartItemEvent,
            onCouponEvent = viewModel::onCouponEvent,
            navigateTo = navigateTo
        )

        ScreenState.ERROR -> ErrorScreen {viewModel.loadCart()}
    }
}