package com.example.shopify.ui.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.ui.navigation.Brand
import com.example.shopify.ui.navigation.Graph
import com.example.shopify.ui.navigation.NavigationBarScreen
import com.example.shopify.ui.screen.cart.CartScreen
import com.example.shopify.ui.screen.category.CategoriesScreen
import com.example.shopify.ui.screen.favourite.FavoriteScreen
import com.example.shopify.ui.screen.home.ui.HomeScreen
import com.example.shopify.ui.screen.me.MeScreen

@Composable
fun HomeGraph(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = NavigationBarScreen.Home.route
    ) {
        brandGraph(navController, paddingValues)

        composable(route = NavigationBarScreen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                paddingValues,
                navigateToProduct = { brandName ->
                    navController.navigate("${Brand.PRODUCTS}/$brandName")
                })
        }
        composable(route = NavigationBarScreen.Category.route) {
            CategoriesScreen()
        }
        composable(route = NavigationBarScreen.Favourite.route) {
            FavoriteScreen()
        }
        composable(route = NavigationBarScreen.Me.route) {
            MeScreen()
        }
        composable(route = NavigationBarScreen.Cart.route) {
            CartScreen()
        }

    }
}