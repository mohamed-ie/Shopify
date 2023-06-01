package com.example.shopify.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.navigation.Graph
import com.example.shopify.screen.home.HomeScreen


@Composable
fun ShopifyGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.LANDING
    ) {

        composable(route = Graph.LANDING){
            navController.navigate(Graph.HOME)
        }

        composable(route = Graph.HOME){
            HomeScreen()
        }

        composable(route = Graph.AUTH){
            navController.navigate(Graph.HOME)
        }

    }
}