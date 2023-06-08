package com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingContent
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.viewModel.ReviewsDetailsViewModel


@Composable
fun ReviewDetailsScreen(
    viewModel:ReviewsDetailsViewModel,
    back:()->Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    val product by viewModel.productState.collectAsState()
    val reviewsState by viewModel.reviewState.collectAsState()

    when(screenState){
        ScreenState.LOADING -> LoadingContent()
        ScreenState.STABLE -> {
            ReviewDetailsScreenContent(
                reviewsState = reviewsState,
                product = product,
                back = back
            )
        }
        ScreenState.ERROR -> ErrorScreen {}
    }

}