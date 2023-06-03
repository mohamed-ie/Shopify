package com.example.shopify.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.ui.navigation.Graph
import com.example.shopify.ui.screen.home.HomeScreen


@Composable
fun ShopifyGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.LANDING
    ) {
        authGraph(navController)

        landingGraph(navController)

        composable(route = Graph.HOME){
            HomeScreen()
        }

    }
}