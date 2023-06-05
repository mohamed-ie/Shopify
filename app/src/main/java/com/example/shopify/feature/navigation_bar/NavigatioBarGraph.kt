package com.example.shopify.feature.navigation_bar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.category.CategoriesScreen
import com.example.shopify.feature.navigation_bar.home.screen.home.ui.HomeScreen
import com.example.shopify.feature.navigation_bar.my_account.MyAccountScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.wishlist.WishlistScreen

@Composable
fun NavigationBarGraph(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = NavigationBarScreen.Home.route
    ) {
        composable(route = NavigationBarScreen.Home.route) {
            HomeScreen(viewModel = hiltViewModel(), paddingValues)
        }
        composable(route = NavigationBarScreen.Category.route) {
            CategoriesScreen()
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