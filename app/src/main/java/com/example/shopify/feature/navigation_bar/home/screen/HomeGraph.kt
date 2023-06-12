package com.example.shopify.feature.navigation_bar.home.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.home.screen.product.ui.ProductScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ProductDetailsScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.view.ReviewDetailsScreen
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.ui.screen.home.ui.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavController, paddingValues: PaddingValues) {
    navigation(
        route = NavigationBarGraph.HOME,
        startDestination = HomeGraph.Home.route
    ) {

        composable(route = HomeGraph.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                paddingValues = paddingValues,
                navigateToProduct = {
                    navController.navigate("${HomeGraph.PRODUCTS}/$it")
                })
        }

        composable("${HomeGraph.PRODUCTS}/{brandName}") {
            ProductScreen(viewModel = hiltViewModel(),
                paddingValues = paddingValues,
                navigateToHome = { navController.popBackStack() },
                navigateToProductDetails = { productId ->
                    navController.navigate("${HomeGraph.PRODUCT_DETAILS}/$productId")
                }
            )
        }

        composable(route = "${HomeGraph.PRODUCT_DETAILS}/{${HomeGraph.PRODUCT_DETAILS_SAVE_ARGS_KEY}}") {
            ProductDetailsScreen(
                viewModel = hiltViewModel(),
                back = {navController.popBackStack()},
                navigateToViewMoreReviews = {productId -> navController.navigate("${HomeGraph.PRODUCT_REVIEW_DETAILS}/$productId")}
            )
        }

        composable(route = "${HomeGraph.PRODUCT_REVIEW_DETAILS}/{${HomeGraph.REVIEW_DETAILS_SAVE_ARGS_KEY}}") {
            ReviewDetailsScreen(
                viewModel = hiltViewModel(),
                back = {navController.popBackStack()}
            )
        }
    }
}

object HomeGraph {
    object Home :
        NavigationBarScreen(route = "HOME", name = R.string.home, icon = Icons.Rounded.Home)

    const val PRODUCTS = "PRODUCTS"
    const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
    const val PRODUCT_DETAILS_SAVE_ARGS_KEY = "PRODUCT_KEY"
    const val REVIEW_DETAILS_SAVE_ARGS_KEY = "REVIEW_DETAILS_KEY"
    const val PRODUCT_REVIEW_DETAILS = "REVIEW_DETAILS"
}