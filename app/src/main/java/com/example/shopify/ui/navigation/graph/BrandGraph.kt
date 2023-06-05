package com.example.shopify.ui.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.ui.navigation.Brand
import com.example.shopify.ui.navigation.Graph
import com.example.shopify.ui.navigation.NavigationBarScreen
import com.example.shopify.ui.screen.Product.ui.ProductScreen

fun NavGraphBuilder.brandGraph(navController: NavController, paddingValues: PaddingValues) {
    navigation(
        route = Graph.HOME,
        startDestination = NavigationBarScreen.Home.route
    ) {
        composable("${Brand.PRODUCTS}/{brandName}") {
            ProductScreen(viewModel = hiltViewModel(),
                paddingValues = paddingValues,
                brandName = it.arguments?.getString("brandName")!!,
                navigateToHome = { navController.popBackStack() }
            )
        }

        composable(route = Brand.PRODUCT_DETAILS) {

        }
    }
}