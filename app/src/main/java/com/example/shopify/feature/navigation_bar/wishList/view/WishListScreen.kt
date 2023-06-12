package com.example.shopify.feature.navigation_bar.wishList.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.R
import com.example.shopify.feature.common.ConfirmationDialog
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.wishList.viewModel.WishListViewModel
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

    LaunchedEffect(lifecycleOwner) {
        viewModel.getWishListProducts()
    }
    when(screenState){
        ScreenState.LOADING ->  LoadingScreen()
        ScreenState.STABLE -> {
            WishListScreenContent(
                productList = productState,
                back = back,
                navigateToProductDetails = navigateToProductDetails,
                deleteProduct = {productId ->
                    viewModel.removeWishProduct(productId)
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