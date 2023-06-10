package com.example.shopify.feature.navigation_bar.cart.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.cart.CartViewModel


@Composable
fun CartScreen(
    innerPadding: PaddingValues,
    viewModel: CartViewModel,
    navigateTo: (route: String, navOptionBuilder: (() -> Unit)?) -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    val state by viewModel.state.collectAsState()
    val itemsState by viewModel.cartItemsState.collectAsState()
    val couponState by viewModel.couponState.collectAsState()

    when (screenState) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> CartScreenContent(
            innerPadding = innerPadding,
            state = state,
            itemsState = itemsState,
            couponState = couponState,
            onCartItemEvent = viewModel::onCartItemEvent,
            onCouponEvent = viewModel::onCouponEvent,
            navigateTo = navigateTo
        )

        ScreenState.ERROR -> {/*TODO*/
        }
    }
}