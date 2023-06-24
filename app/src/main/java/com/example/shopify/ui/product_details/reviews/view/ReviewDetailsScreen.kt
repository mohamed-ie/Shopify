package com.example.shopify.ui.product_details.reviews.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.ui.common.screen.ErrorScreen
import com.example.shopify.ui.common.screen.LoadingScreen
import com.example.shopify.ui.common.state.ScreenState
import com.example.shopify.ui.product_details.reviews.viewModel.ReviewsDetailsViewModel


@Composable
fun ReviewDetailsScreen(
    viewModel: ReviewsDetailsViewModel,
    back:()->Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    val product by viewModel.productState.collectAsState()
    val reviewsState by viewModel.reviewState.collectAsState()

    when(screenState){
        ScreenState.LOADING -> LoadingScreen()
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