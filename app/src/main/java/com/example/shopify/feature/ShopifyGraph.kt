package com.example.shopify.feature

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.feature.auth.authGraph
import com.example.shopify.feature.landing.landingGraph
import com.example.shopify.feature.navigation_bar.HomeNavigationBarScreen


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
            HomeNavigationBarScreen()
        }

    }
}

object Graph {
    const val ROOT = "ROOT_GRAPH"
    const val LANDING = "LANDING_GRAPH"
    const val AUTH = "AUTH_GRAPH"
    const val HOME = "HOME_GRAPH"
}
