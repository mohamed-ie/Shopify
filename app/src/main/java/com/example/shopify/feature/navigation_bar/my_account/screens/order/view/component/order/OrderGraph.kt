package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.shopify.feature.Graph
import com.example.shopify.ui.screen.order.OrdersScreen

fun NavGraphBuilder.orderGraph(navController: NavController) {
    navigation(
        route = Graph.ORDERS,
        startDestination = OrderGraph.ORDER
    ) {
        composable(route = OrderGraph.ORDER) {
            OrdersScreen()
        }
    }
}

object OrderGraph {
    const val ORDER = "ORDER"
}