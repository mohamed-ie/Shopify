package com.example.shopify.feature.navigation_bar.my_account

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.address.addressGraph
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view.MyAccountScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ProductDetailsScreen
import com.example.shopify.feature.wishList.view.WishListScreen
import com.example.shopify.helpers.firestore.mapper.encodeProductId


fun NavGraphBuilder.myAccountGraph(paddingValues: PaddingValues, navController: NavHostController) {
    navigation(
        route = NavigationBarGraph.MY_ACCOUNT,
        startDestination = MyAccountGraph.MyAccount.route
    ) {
        composable(route = MyAccountGraph.MyAccount.route) {
            MyAccountScreen(
                innerPadding = paddingValues,
                viewModel = hiltViewModel(),
                navigateTo = { navController.navigate(it) },
                back = { navController.popBackStack() }
            )
        }

        composable(route = MyAccountGraph.PROFILE) {

        }

        composable(route = MyAccountGraph.ERROR) {
            ErrorScreen { navController.popBackStack() }
        }

        composable(route = MyAccountGraph.WISHLIST) {
            WishListScreen(
                viewModel = hiltViewModel(),
                back = { navController.popBackStack() },
                navigateToProductDetails = { productId -> navController.navigate("${MyAccountGraph.PRODUCT_DETAILS}/${productId.encodeProductId()}") })
        }

        composable(route = "${MyAccountGraph.PRODUCT_DETAILS}/{${MyAccountGraph.PRODUCT_DETAILS_SAVE_ARGS_KEY}}") {
            ProductDetailsScreen(
                viewModel = hiltViewModel(),
                navigateToViewMoreReviews ={},
                back = {navController.popBackStack()})
        }
        addressGraph(navController)

    }
}


object MyAccountGraph {
    object MyAccount : NavigationBarScreen(
        route = "MY_ACCOUNT",
        name = R.string.my_account,
        icon = Icons.Rounded.Person
    )

    const val ADDRESSES = "ADDRESSES"
    const val ADD_ADDRESS = "ADD_ADDRESS"
    const val PROFILE = "PROFILE"
    const val ERROR = "PROFILE"
    const val WISHLIST = "WISHLIST"
    const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
    const val PRODUCT_DETAILS_SAVE_ARGS_KEY = "PRODUCT_KEY"
}