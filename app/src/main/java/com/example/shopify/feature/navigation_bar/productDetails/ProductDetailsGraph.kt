package com.example.shopify.feature.navigation_bar.productDetails

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.cart.CartGraph
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ProductDetailsScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.view.ReviewDetailsScreen
import com.example.shopify.helpers.firestore.mapper.encodeProductId

fun NavGraphBuilder.productDetailsGraph(navController: NavController) {
    navigation(
        route = Graph.PRODUCT_DETAILS,
        startDestination = ProductDetailsGraph.PRODUCT_DETAILS
    ) {

        composable(
            route = "${ProductDetailsGraph.PRODUCT_DETAILS}/{${ProductDetailsGraph.PRODUCT_DETAILS_SAVE_ARGS_KEY}}",
            arguments = listOf(
                navArgument(ProductDetailsGraph.PRODUCT_DETAILS_SAVE_ARGS_KEY) {
                    type = NavType.StringType
                })
        ) {
            ProductDetailsScreen(
                viewModel = hiltViewModel(),
                back = { navController.popBackStack() },
                navigateToViewMoreReviews = { productId ->
                    navController.navigate("${ProductDetailsGraph.PRODUCT_REVIEW_DETAILS}/${productId.encodeProductId()}")
                },
                navigateToCart = {
                    navController.navigate(CartGraph.Cart.route)
                },
                navigateToSearch = {
                    navController.navigate(NavigationBarGraph.SEARCH)
                }
            )
        }

        composable(route = "${ProductDetailsGraph.PRODUCT_REVIEW_DETAILS}/{${ProductDetailsGraph.REVIEW_DETAILS_SAVE_ARGS_KEY}}") {
            ReviewDetailsScreen(
                viewModel = hiltViewModel(),
                back = { navController.popBackStack() }
            )
        }
    }
}

object ProductDetailsGraph {
    const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
    const val PRODUCT_DETAILS_SAVE_ARGS_KEY = "PRODUCT_DETAILS_SAVE_ARGS_KEY"
    const val REVIEW_DETAILS_SAVE_ARGS_KEY = "REVIEW_DETAILS_SAVE_ARGS_KEY"
    const val PRODUCT_REVIEW_DETAILS = "PRODUCT_REVIEW_DETAILS"
}