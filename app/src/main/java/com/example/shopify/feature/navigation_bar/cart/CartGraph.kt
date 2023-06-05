package com.example.shopify.feature.navigation_bar.cart

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.feature.navigation_bar.cart.view.CartScreen

fun NavGraphBuilder.cartNavigation(innerPadding: PaddingValues, navController: NavController) {
    navigation(route = "", startDestination = "") {
        composable(route = "") {
            CartScreen(innerPadding = innerPadding, viewModel = hiltViewModel()){_,_->

            }
        }
    }
}
