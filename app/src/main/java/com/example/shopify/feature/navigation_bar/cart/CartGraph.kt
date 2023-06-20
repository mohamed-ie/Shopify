package com.example.shopify.feature.navigation_bar.cart

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.cart.view.CartScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.order.CheckoutViewModel
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment.CreditCardInfoScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view.CheckoutScreen

fun NavGraphBuilder.cartNavigation(navController: NavController) {
    navigation(route = NavigationBarGraph.CART, startDestination = CartGraph.Cart.route) {
        composable(route = CartGraph.Cart.route) {
            CartScreen(
                viewModel = hiltViewModel(),
                navigateTo = navController::navigate,
            )
        }

        composable(route = CartGraph.CHECK_OUT) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(CartGraph.CHECK_OUT)
            }
            CheckoutScreen(viewModel = hiltViewModel(parentEntry),
                back = { navController.popBackStack() },
                navigatePopUpTo = { route, pop ->
                    navController.navigate(route = route) {
                        pop?.let { it1 -> popUpTo(route = it1) }
                    }
                }
            )
        }
        composable(route = CartGraph.CREDIT_CARD_INFO) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(CartGraph.CHECK_OUT)
            }
            val viewModel: CheckoutViewModel = hiltViewModel(parentEntry)
            CreditCardInfoScreen(
                viewModel = viewModel,
                back = navController::popBackStack,
                navigatePopUpTo = { route, pop ->
                    navController.navigate(route = route) {
                        popUpTo(route = pop)
                    }
                }
            )
        }

//        addressGraph(navController)
        // checkoutGraph(navController)
    }
}

object CartGraph {
    const val CHECK_OUT = "CHECK_OUT"
    const val CREDIT_CARD_INFO = "CREDIT_CARD"

    object Cart :
        NavigationBarScreen(
            route = "CART",
            name = R.string.cart,
            unSelectedIcon = Icons.TwoTone.ShoppingCart,
            selectedIcon = Icons.Outlined.ShoppingCart
        )
}
