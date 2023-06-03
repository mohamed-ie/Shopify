package com.example.shopify.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.ui.navigation.Graph
import com.example.shopify.ui.screen.home.ui.HomeNavigationBarScreen


@Composable
fun ShopifyGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        authGraph(navController)

        composable(route = Graph.LANDING){
            navController.navigate(Graph.AUTH)
        }

        composable(route = Graph.HOME){
            HomeNavigationBarScreen()
        }

    }
}