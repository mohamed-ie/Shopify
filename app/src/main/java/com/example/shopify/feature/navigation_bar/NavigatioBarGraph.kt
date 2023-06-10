package com.example.shopify.ui.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.category.CategoriesScreen
import com.example.shopify.feature.navigation_bar.home.screen.homeGraph
import com.example.shopify.feature.navigation_bar.my_account.myAccountGraph

@Composable
fun NavigationBarGraph(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = NavigationBarGraph.HOME
    ) {

        composable(route = NavigationBarGraph.CATEGORY) {
            CategoriesScreen()
        }

        myAccountGraph(paddingValues, navController)
        homeGraph(navController, paddingValues)

    }
}

object NavigationBarGraph {
    const val HOME = "HOME_GRAPH"
    const val CATEGORY = "CATEGORY_GRAPH"
    const val MY_ACCOUNT = "MY_ACCOUNT_GRAPH"
    const val CART = "CART_GRAPH"
}