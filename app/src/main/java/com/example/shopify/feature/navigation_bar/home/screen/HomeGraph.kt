package com.example.shopify.feature.navigation_bar.home.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.home.screen.home.ui.HomeScreen
import com.example.shopify.feature.navigation_bar.home.screen.product.ui.ProductScreen

fun NavGraphBuilder.homeGraph(navController: NavHostController, paddingValues: PaddingValues) {
    navigation(
        route = NavigationBarGraph.HOME,
        startDestination = HomeGraph.Home.route
    ) {

        composable(route = HomeGraph.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navigateTo = navController::navigate
            )
        }

        composable("${HomeGraph.PRODUCTS}/{brandName}") {
            ProductScreen(
                viewModel = hiltViewModel(),
                back = navController::popBackStack,
                navigateTo = navController::navigate
            )
        }
    }
}

object HomeGraph {
    object Home :
        NavigationBarScreen(route = "HOME", name = R.string.home, icon = Icons.Rounded.Home)

    const val PRODUCTS = "PRODUCTS"
}