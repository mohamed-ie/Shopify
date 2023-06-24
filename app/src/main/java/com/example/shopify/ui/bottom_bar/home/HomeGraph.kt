package com.example.shopify.ui.bottom_bar.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.twotone.Home
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.NavigationBarGraph
import com.example.shopify.ui.bottom_bar.NavigationBarScreen
import com.example.shopify.ui.bottom_bar.home.home.ui.HomeScreen
import com.example.shopify.ui.bottom_bar.home.product.ui.ProductScreen

fun NavGraphBuilder.homeGraph(navController: NavHostController, paddingValues: PaddingValues) {
    navigation(
        route = NavigationBarGraph.HOME,
        startDestination = HomeGraph.Home.route
    ) {

        composable(route = HomeGraph.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navigateTo = navController::navigate,
                navigateToSearch = {
                    navController.navigate(NavigationBarGraph.SEARCH)
                }
            )
        }

        composable("${HomeGraph.PRODUCTS}/{brandName}") {
            ProductScreen(
                viewModel = hiltViewModel(),
                back = navController::popBackStack,
                navigateTo = navController::navigate,
                navigateToSearch = {
                    navController.navigate(NavigationBarGraph.SEARCH)
                }
            )
        }
    }
}

object HomeGraph {
    object Home :
        NavigationBarScreen(
            route = "HOME",
            name = R.string.home,
            unSelectedIcon = Icons.TwoTone.Home,
            selectedIcon = Icons.Outlined.Home
        )

    const val PRODUCTS = "PRODUCTS"
}