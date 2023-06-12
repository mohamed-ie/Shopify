package com.example.shopify.feature.navigation_bar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.cart.cartNavigation
import com.example.shopify.feature.navigation_bar.category.categoryGraph
import com.example.shopify.feature.navigation_bar.home.screen.homeGraph
import com.example.shopify.feature.navigation_bar.my_account.myAccountGraph

@Composable
fun NavigationBarGraph(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = NavigationBarGraph.HOME
    ) {
        myAccountGraph(paddingValues, navController)
        homeGraph(navController, paddingValues)
        categoryGraph(paddingValues, navController)
        cartNavigation(navController)
    }
}

object NavigationBarGraph {
    const val HOME = "HOME_GRAPH"
    const val CATEGORY = "CATEGORY_GRAPH"
    const val MY_ACCOUNT = "MY_ACCOUNT_GRAPH"
    const val CART = "CART_GRAPH"
}