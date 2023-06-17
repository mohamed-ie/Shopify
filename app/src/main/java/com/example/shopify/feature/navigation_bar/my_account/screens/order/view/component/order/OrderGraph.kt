package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.view.OrderDetails
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.view.OrdersScreen

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
            "${OrderGraph.ORDER_DETAILS}/{order}",
            arguments = listOf(navArgument("order") {
                type = NavType.SerializableType(Order::class.java)
            })
        ) { backStackEntry ->
            val order = backStackEntry.arguments?.getSerializable("order") as Order
            OrderDetails(order = order, back = { navController.popBackStack() })
        }
    }
}

object OrderGraph {
    const val ORDER = "ORDER"
    const val ORDER_DETAILS = "ORDER_DETAILS"
}