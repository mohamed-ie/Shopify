package com.example.shopify.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.ui.bottom_bar.HomeNavigationBarScreen
import com.example.shopify.ui.landing.landingGraph


@Composable
fun ShopifyGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.LANDING
    ) {

        landingGraph(navController)

        composable(route = Graph.HOME){
            HomeNavigationBarScreen()
        }


    }
}

object Graph {
    const val ADDRESS ="ADDRESS_GRAPH"
    const val ROOT = "ROOT_GRAPH"
    const val LANDING = "LANDING_GRAPH"
    const val AUTH = "AUTH_GRAPH"
    const val HOME = "HOME_GRAPH"
    const val CHECK_OUT = "CHECK_OUT"
    const val WISH_LIST = "WISH_LIST_GRAPH"
    const val PRODUCT_DETAILS = "PRODUCT_DETAILS_GRAPH"
    const val ORDERS = "ORDERS"
}
