package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.viewModel.ProductDetailsViewModel
import com.shopify.graphql.support.ID


@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    navigateToViewMoreReviews:(ID)->Unit,
    navigateToCart:()->Unit,
    back:()->Unit,
    navigateToSearch: () -> Unit
) {
    val product by viewModel.productState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val addToCardState by viewModel.addToCardState.collectAsState()
    val variantsState by viewModel.variantState.collectAsState()
    val reviewsState by viewModel.reviewState.collectAsState()

    when(screenState){
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> {
            ProductDetailsScreenContent(
                product = product,
                addToCardState = addToCardState,
                variantsState = variantsState,
                reviewsState = reviewsState,
                viewReviewsMore = {navigateToViewMoreReviews(viewModel.productId)},
                back = {back()},
                viewCart = navigateToCart,
                onFavouriteClick = {isFavourite -> viewModel.sendFavouriteAction(isFavourite)},
                navigateToSearch = navigateToSearch
            )
        }
        ScreenState.ERROR -> ErrorScreen {}
    }

}