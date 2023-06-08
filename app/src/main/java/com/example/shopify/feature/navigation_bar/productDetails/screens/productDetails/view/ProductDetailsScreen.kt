package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingContent
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.viewModel.ProductDetailsViewModel


@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    navigateToViewMoreReviews:(String)->Unit,
    back:()->Unit
) {
    val product by viewModel.productState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val addToCardState by viewModel.addToCardState.collectAsState()
    val variantsState by viewModel.variantState.collectAsState()
    val reviewsState by viewModel.reviewState.collectAsState()

    when(screenState){
        ScreenState.LOADING -> LoadingContent()
        ScreenState.STABLE -> {
            ProductDetailsScreenContent(
                product = product,
                addToCardState = addToCardState,
                variantsState = variantsState,
                reviewsState = reviewsState,
                viewReviewsMore = {navigateToViewMoreReviews(viewModel.productId)},
                back = {back()},
                viewCart = {}
            )
        }
        ScreenState.ERROR -> ErrorScreen {}
    }

}