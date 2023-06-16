package com.example.shopify.feature.navigation_bar.cart.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.feature.navigation_bar.cart.CartViewModel
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState


@Composable
fun CartScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: CartViewModel,
    navigateTo: (String) -> Unit
) {
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.loadCart()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val screenState by viewModel.screenState.collectAsState()
    val cart by viewModel.state.collectAsState()
    val itemsState by viewModel.cartLinesState.collectAsState()
    val couponState by viewModel.couponState.collectAsState()

    when (screenState) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> CartScreenContent(
            cart = cart,
            itemsState = itemsState,
            couponState = couponState,
            onCartItemEvent = viewModel::onCartItemEvent,
            onCouponEvent = viewModel::onCouponEvent,
            navigateTo = navigateTo
        )

        ScreenState.ERROR -> ErrorScreen {viewModel.loadCart()}
    }
}