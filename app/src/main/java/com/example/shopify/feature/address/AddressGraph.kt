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
import com.example.shopify.feature.navigation_bar.common.GraphRouteWithArgs

fun NavGraphBuilder.addressGraph(navController: NavController) {
    navigation(route = Graph.ADDRESS, startDestination = AddressGraph.Addresses.routeWithArgs) {
        composable(
            route = AddressGraph.Addresses.routeWithArgs,
            arguments = AddressGraph.Addresses.args
        ) { backStackEntry ->
            AddressesScreen(
                pickShipping = backStackEntry.arguments?.getBoolean(AddressGraph.Addresses.args[0].name)
                    ?: false,
                pickBilling = backStackEntry.arguments?.getBoolean(AddressGraph.Addresses.args[1].name)
                    ?: false,
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