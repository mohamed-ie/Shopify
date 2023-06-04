package com.example.shopify.ui.screen.cart.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.ui.screen.cart.CartViewModel
import com.example.shopify.ui.screen.common.LoadingContent
import com.example.shopify.ui.screen.common.model.ScreenState


@Composable
fun CartScreen(
    innerPadding: PaddingValues,
    viewModel: CartViewModel,
    navigateTo:(route: String, navOptionBuilder: (() -> Unit)?) -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    val state by viewModel.state.collectAsState()
    val itemsState by viewModel.cartItemsState.collectAsState()
    val couponState by viewModel.couponState.collectAsState()

    when (screenState) {
        ScreenState.LOADING -> LoadingContent()
        ScreenState.STABLE -> CartScreenContent(
            innerPadding = innerPadding,
            state = state,
            itemsState = itemsState,
            couponState = couponState,
            onCartItemEvent = viewModel::onCartItemEvent,
            onCouponEvent = viewModel::onCouponEvent,
            navigateTo = navigateTo
        )

        ScreenState.ERROR -> {/*TODO*/}
    }
}