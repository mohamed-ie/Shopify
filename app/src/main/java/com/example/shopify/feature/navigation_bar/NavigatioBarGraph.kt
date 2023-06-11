package com.example.shopify.ui.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.category.view.CategoryScreen
import com.example.shopify.feature.navigation_bar.home.screen.Brand
import com.example.shopify.feature.navigation_bar.home.screen.homeGraph
import com.example.shopify.feature.navigation_bar.my_account.MyAccountScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.wishlist.WishlistScreen
import com.example.shopify.ui.screen.home.ui.HomeScreen

@Composable
fun NavigationBarGraph(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = NavigationBarScreen.Home.route
    ) {
        homeGraph(navController, paddingValues)

        composable(route = NavigationBarScreen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                paddingValues,
                navigateToProduct = { brandName ->
                    navController.navigate("${Brand.PRODUCTS}/$brandName")
                })
        }
        composable(route = NavigationBarScreen.Category.route) {
            CategoryScreen(
                hiltViewModel(),
                paddingValues,
                navigateTo = { productId ->
                    navController.navigate("${Brand.PRODUCT_DETAILS}/$productId")
                })
        }
        composable(route = NavigationBarScreen.Favourite.route) {
            WishlistScreen()
        }
        composable(route = NavigationBarScreen.Me.route) {
            MyAccountScreen()
        }
        composable(route = NavigationBarScreen.Cart.route) {
//            CartScreen()
        }

    }
}