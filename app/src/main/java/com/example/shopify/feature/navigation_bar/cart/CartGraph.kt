package com.example.shopify.feature.navigation_bar.cart

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.address.addressGraph
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.cart.view.CartScreen
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.order.OrderViewModel
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

        composable(route = CartGraph.ERROR) {
            ErrorScreen { navController.popBackStack() }
        }
        composable(route = CartGraph.CHECK_OUT) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(CartGraph.CHECK_OUT)
            }
            CheckoutScreen(viewModel = hiltViewModel(parentEntry),
                navigateTo = navController::navigate,
                back = { navController.popBackStack() }
            )
        }
        composable(route = CartGraph.CREDIT_CARD) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(CartGraph.CHECK_OUT)
            }
            val viewModel: OrderViewModel = hiltViewModel(parentEntry)
            CreditCardInfoScreen(
                state = viewModel.creditCardInfoState.collectAsState().value,
                onEvent = viewModel::onCreditCardEvent
            )
        }

        addressGraph(navController)
        // checkoutGraph(navController)
    }
}

object CartGraph {
    const val ERROR = "ERROR"
    const val CHECK_OUT = "CHECK_OUT"
    const val CREDIT_CARD = "CREDIT_CARD"

    object Cart :
        NavigationBarScreen(route = "CART", name = R.string.cart, icon = Icons.Rounded.ShoppingCart)
}
