package com.example.shopify.ui.address

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.shopify.helpers.GraphRouteWithArgs
import com.example.shopify.ui.Graph
import com.example.shopify.ui.address.add_address.view.AddAddressScreen
import com.example.shopify.ui.address.addresses.view.AddressesScreen

fun NavGraphBuilder.addressGraph(navController: NavController) {
    navigation(route = Graph.ADDRESS, startDestination = AddressGraph.Addresses.routeWithArgs) {
        composable(
            route = AddressGraph.Addresses.routeWithArgs,
            arguments = AddressGraph.Addresses.args
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Graph.ADDRESS)
            }
            AddressesScreen(
                pickShipping = backStackEntry.arguments?.getBoolean(AddressGraph.Addresses.args[0].name)
                    ?: false,
                pickBilling = backStackEntry.arguments?.getBoolean(AddressGraph.Addresses.args[1].name)
                    ?: false,
                viewModel = hiltViewModel(),
                addressViewModel = hiltViewModel(parentEntry),
                back = { navController.popBackStack() },
                navigateTo = { navController.navigate(it) }
            )
        }

        composable(route = AddressGraph.ADD_ADDRESS) { backStackEntry->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Graph.ADDRESS)
            }
            AddAddressScreen(
                viewModel = hiltViewModel(),
                addressViewModel= hiltViewModel(parentEntry),
                back = { navController.popBackStack() }
            )
        }

    }
}

object AddressGraph {
    val Addresses = GraphRouteWithArgs(
        route = "ADDRESSES",
        routeWithArgs = "ADDRESSES?pickShipping={pickShipping}&pickBilling={pickBilling}",
        routeToBeFormatted = "ADDRESSES?pickShipping=%s&pickBilling=%s",
        args = listOf(navArgument("pickShipping") {
            type = NavType.BoolType
            defaultValue = false
        }, navArgument("pickBilling") {
            type = NavType.BoolType
            defaultValue = false
        })
    )

    const val ADD_ADDRESS = "ADD_ADDRESS"
}