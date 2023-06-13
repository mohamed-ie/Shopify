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
import com.example.shopify.feature.navigation_bar.home.screen.product.ui.ProductScreen
import com.example.shopify.feature.navigation_bar.productDetails.ProductDetailsGraph
import com.example.shopify.helpers.firestore.mapper.encodeProductId
import com.example.shopify.ui.screen.home.ui.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavHostController, paddingValues: PaddingValues) {
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
                    navController.navigate("${ProductDetailsGraph.PRODUCT_DETAILS}/${productId.encodeProductId()}")
                }
            )
        }
    }
}

object HomeGraph {
    object Home :
        NavigationBarScreen(route = "HOME", name = R.string.home, icon = Icons.Rounded.Home)

    const val PRODUCTS = "PRODUCTS"
}