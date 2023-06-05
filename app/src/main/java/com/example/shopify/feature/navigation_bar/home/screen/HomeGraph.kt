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

fun NavGraphBuilder.homeGraph(navController: NavController, paddingValues: PaddingValues) {
    navigation(
        route = Graph.HOME,
        startDestination = NavigationBarScreen.Home.route
    ) {
        composable("${Brand.PRODUCTS}/{brandName}") {
            ProductScreen(viewModel = hiltViewModel(),
                paddingValues = paddingValues,
                navigateToHome = { navController.popBackStack() }
            )
        }

        composable(route = Brand.PRODUCT_DETAILS) {

        }
    }
}

object Brand {
    const val PRODUCTS = "PRODUCTS"
    const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
}