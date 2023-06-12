package com.example.shopify.feature.navigation_bar.wishList.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.wishList.viewModel.WishListViewModel
import com.shopify.graphql.support.ID


@Composable
fun WishListScreen(
    viewModel: WishListViewModel,
    back:() -> Unit,
    navigateToProductDetails:(ID)->Unit
) {
    val productState by viewModel.productsState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()

    when(screenState){
        ScreenState.LOADING ->  LoadingScreen()
        ScreenState.STABLE -> {
            WishListScreenContent(
                productList = productState,
                back = back,
                navigateToProductDetails = navigateToProductDetails
            )
        }
        ScreenState.ERROR -> ErrorScreen {viewModel.getWishListProducts()}
    }
}