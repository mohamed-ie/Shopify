package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.viewModel.ProductDetailsViewModel
import com.shopify.graphql.support.ID


@Composable
fun ProductDetailsScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: ProductDetailsViewModel,
    navigateToViewMoreReviews: (ID) -> Unit,
    navigateToCart: () -> Unit,
    back: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit
) {


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.loadProductDetails()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val product by viewModel.productState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val addToCardState by viewModel.addToCardState.collectAsState()
    val variantsState by viewModel.variantState.collectAsState()
    val reviewsState by viewModel.reviewState.collectAsState()

    when (screenState) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> {
            ProductDetailsScreenContent(
                product = product,
                addToCardState = addToCardState,
                variantsState = variantsState,
                reviewsState = reviewsState,
                viewReviewsMore = { navigateToViewMoreReviews(viewModel.productId) },
                back = { back() },
                viewCart = navigateToCart,
                onFavouriteClick = { isFavourite ->
                    product.isLogged
                    if (product.isLogged)
                        viewModel.sendFavouriteAction(isFavourite)
                    else
                        navigateToAuth()

                },
                navigateToSearch = navigateToSearch,
                navigateToAuth = navigateToAuth
            )
        }
        ScreenState.ERROR -> ErrorScreen {}
    }

}