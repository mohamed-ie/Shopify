package com.example.shopify.feature.navigation_bar.cart

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.address.addressGraph
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.cart.view.CartScreen

fun NavGraphBuilder.cartNavigation(navController: NavController) {
    navigation(route = NavigationBarGraph.CART, startDestination = CartGraph.Cart.route) {
        composable(route = CartGraph.Cart.route) {
            CartScreen(
                viewModel = hiltViewModel(),
                navigateTo = navController::navigate
            )
        }

        composable(route = CartGraph.ERROR) {
            ErrorScreen { navController.popBackStack() }
        }

        addressGraph(navController)
    }
}

object CartGraph {
    const val ERROR = "ERROR"
    object Cart :
        NavigationBarScreen(route = "CART", name = R.string.cart, icon = Icons.Rounded.ShoppingCart)
}
