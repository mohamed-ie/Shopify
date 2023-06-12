package com.example.shopify.feature.address

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.shopify.feature.Graph
import com.example.shopify.feature.address.add_address.view.AddAddressScreen
import com.example.shopify.feature.address.addresses.view.AddressesScreen
import com.example.shopify.feature.common.GraphRouteWithArgs

fun NavGraphBuilder.addressGraph(navController: NavController) {
    navigation(route = Graph.ADDRESS, startDestination = AddressGraph.Addresses.routeWithArgs) {
        composable(
            route = AddressGraph.Addresses.routeWithArgs,
            arguments = AddressGraph.Addresses.args
        ) { backStackEntry ->
            AddressesScreen(
                allowPick = backStackEntry.arguments?.getBoolean(AddressGraph.Addresses.args[0].name) ?: false,
                viewModel = hiltViewModel(),
                back = { navController.popBackStack() },
                navigateTo = { navController.navigate(it) }
            )
        }

        composable(route = AddressGraph.ADD_ADDRESS) {
            AddAddressScreen(viewModel = hiltViewModel(), back = { navController.popBackStack() })
        }

    }
}

object AddressGraph {
    val Addresses = GraphRouteWithArgs(
        route = "ADDRESSES",
        routeWithArgs = "ADDRESSES?allowPick={allowPick}",
        routeToBeFormatted= "ADDRESSES?allowPick=%s",
        args = listOf(navArgument("allowPick") {
            type = NavType.BoolType
            defaultValue = false
        })
    )

    const val ADD_ADDRESS = "ADD_ADDRESS"
}