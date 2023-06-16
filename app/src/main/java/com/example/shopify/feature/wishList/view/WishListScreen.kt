package com.example.shopify.feature.wishList.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.ConfirmationDialog
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.wishList.viewModel.WishListViewModel
import com.shopify.graphql.support.ID


@Composable
fun WishListScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: WishListViewModel,
    back:() -> Unit,
    navigateToProductDetails:(ID)->Unit
) {
    val productState by viewModel.productsState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val dialogVisibilityState by viewModel.dialogVisibilityState.collectAsState()
    val bottomSheetState by viewModel.wishedBottomSheetState.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getWishListProducts()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when(screenState){
        ScreenState.LOADING ->  LoadingScreen()
        ScreenState.STABLE -> {
            WishListScreenContent(
                productList = productState,
                bottomSheetState = bottomSheetState,
                continueShopping = {viewModel.sendContinueShopping()},
                viewCart = {},
                back = back,
                navigateToProductDetails = navigateToProductDetails,
                deleteProduct = {productId ->
                    viewModel.removeWishProduct(productId)
                },
                addToCart = {productId ->
                    viewModel.addToCart(productId)
                }
            )
        }
        ScreenState.ERROR -> ErrorScreen {viewModel.getWishListProducts()}
    }

    ConfirmationDialog(
        visible = dialogVisibilityState,
        message = stringResource(R.string.delete_alert_message),
        onDismiss = {viewModel.dismissDeleteDialog()},
        onConfirm = {viewModel.confirmDeletedProduct()}
    )
}