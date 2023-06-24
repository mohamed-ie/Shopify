package com.example.shopify.ui.order

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.shopify.ui.Graph
import com.example.shopify.ui.order.orders.OrdersScreen
import com.example.shopify.ui.order.order_details.OrderDetails

fun NavGraphBuilder.orderGraph(navController: NavController) {
    navigation(
        route = Graph.ORDERS,
        startDestination = OrderGraph.ORDER
    ) {
        composable(route = OrderGraph.ORDER) {
            OrdersScreen(viewModel = hiltViewModel(),
                navigateTo = navController::navigate,
                back = { navController.popBackStack() })
        }
        composable(
            route = OrderGraph.ORDER_DETAILS
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(OrderGraph.ORDER)
            }
            OrderDetails(hiltViewModel(parentEntry), back = { navController.popBackStack() })
        }
    }
}

object OrderGraph {
    const val ORDER = "ORDER"
    const val ORDER_DETAILS = "ORDER_DETAILS"
}