package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.my_account.screens.order.CheckoutViewModel
import com.example.shopify.feature.navigation_bar.my_account.screens.order.ReviewUIEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.OrderGraph
import com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.view.components.ReviewInputBottomSheet
import com.example.shopify.ui.screen.order.OrdersScreenContent

@Composable
fun OrdersScreen(
    viewModel: CheckoutViewModel,
    navigateTo: (String) -> Unit,
    back: () -> Unit
) {
    val reviewState by viewModel.reviewState.collectAsState()

    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> OrdersScreenContent(
            orders = viewModel.orderList.collectAsState().value,
            back = back,
            viewOrderDetails = {
                viewModel.orderIndex = it
                navigateTo(OrderGraph.ORDER_DETAILS)
            },
            viewAddReview = {orderIndex, lineIndex ->
                viewModel.onReviewEvent(ReviewUIEvent.View(orderIndex,lineIndex))
            }
            )

        ScreenState.ERROR -> ErrorScreen {

        }
    }

    AnimatedVisibility(
        visible = reviewState.visible,
        enter = expandVertically(expandFrom = Alignment.Bottom, animationSpec = tween(2000)),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom, animationSpec = tween(2000))
    ) {
        ReviewInputBottomSheet(
            onReviewTitleChange = {
                viewModel.onReviewEvent(ReviewUIEvent.SendTitle(it))
            },
            onReviewContentChange = {
                viewModel.onReviewEvent(ReviewUIEvent.SendContent(it))
            },
            onRatingSelected = {
                viewModel.onReviewEvent(ReviewUIEvent.SendRate(it))
            },
            onCloseBottomSheet = {
                viewModel.onReviewEvent(ReviewUIEvent.Close)
            },
            onSubmitReviewClick = {
                viewModel.onReviewEvent(ReviewUIEvent.Submit)
            },
            reviewState = reviewState
        )
    }
}