package com.example.shopify.feature.navigation_bar.home.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.home.screen.product.ui.ProductScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ProductDetailsScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.view.ReviewDetailsScreen

fun NavGraphBuilder.homeGraph(navController: NavController, paddingValues: PaddingValues) {
    navigation(
        route = Graph.HOME,
        startDestination = NavigationBarScreen.Home.route
    ) {
        composable("${Brand.PRODUCTS}/{brandName}") {
            ProductScreen(viewModel = hiltViewModel(),
                paddingValues = paddingValues,
                navigateToHome = { navController.popBackStack() },
                navigateToProductDetails = { productId ->
                    navController.navigate("${Brand.PRODUCT_DETAILS}/$productId")
                }
            )
        }

        composable(route = "${Brand.PRODUCT_DETAILS}/{${Brand.PRODUCT_DETAILS_SAVE_ARGS_KEY}}") {
            ProductDetailsScreen(
                viewModel = hiltViewModel(),
                back = {navController.popBackStack()},
                navigateToViewMoreReviews = {productId -> navController.navigate("${Brand.PRODUCT_REVIEW_DETAILS}/$productId")}
            )
        }
        composable(route = "${Brand.PRODUCT_REVIEW_DETAILS}/{${Brand.REVIEW_DETAILS_SAVE_ARGS_KEY}}") {
            ReviewDetailsScreen(
                viewModel = hiltViewModel(),
                back = {navController.popBackStack()}
            )
        }
    }
}

object Brand {
    const val PRODUCTS = "PRODUCTS"
    const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
    const val PRODUCT_DETAILS_SAVE_ARGS_KEY = "PRODUCT_KEY"
    const val REVIEW_DETAILS_SAVE_ARGS_KEY = "REVIEW_DETAILS_KEY"
    const val PRODUCT_REVIEW_DETAILS = "REVIEW_DETAILS"
}