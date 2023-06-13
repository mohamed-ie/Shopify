package com.example.shopify.feature.navigation_bar.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.category.view.CategoryScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ProductDetailsScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.view.ReviewDetailsScreen
import com.example.shopify.helpers.firestore.mapper.encodeProductId

fun NavGraphBuilder.categoryGraph(paddingValues: PaddingValues, navController: NavHostController) {
    navigation(
        route = NavigationBarGraph.CATEGORY,
        startDestination = CategoryGraph.Category.route
    ) {
        composable(route = CategoryGraph.Category.route) {
            CategoryScreen(
                viewModel = hiltViewModel(),
                paddingValues = paddingValues,
                navigateTo = { productId ->
                    navController.navigate("${CategoryGraph.PRODUCT_DETAILS}/${productId.encodeProductId()}")
                }
            )
        }

        composable(route = "${CategoryGraph.PRODUCT_DETAILS}/{${CategoryGraph.PRODUCT_DETAILS_SAVE_ARGS_KEY}}") {
            ProductDetailsScreen(
                viewModel = hiltViewModel(),
                back = { navController.popBackStack() },
                navigateToViewMoreReviews = { productId -> navController.navigate("${CategoryGraph.PRODUCT_REVIEW_DETAILS}/${productId.encodeProductId()}") }
            )
        }
        composable(route = "${CategoryGraph.PRODUCT_REVIEW_DETAILS}/{${CategoryGraph.REVIEW_DETAILS_SAVE_ARGS_KEY}}") {
            ReviewDetailsScreen(
                viewModel = hiltViewModel(),
                back = { navController.popBackStack() }
            )
        }

    }
}


object CategoryGraph {
    object Category : NavigationBarScreen(
        route = "CATEGORY",
        name = R.string.category,
        icon = Icons.Rounded.Category
    )

    const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
    const val PRODUCT_REVIEW_DETAILS = "PRODUCT_REVIEW_DETAILS"
    const val PRODUCT_DETAILS_SAVE_ARGS_KEY = "PRODUCT_DETAILS_SAVE_ARGS_KEY"
    const val REVIEW_DETAILS_SAVE_ARGS_KEY = "REVIEW_DETAILS_SAVE_ARGS_KEY"

}