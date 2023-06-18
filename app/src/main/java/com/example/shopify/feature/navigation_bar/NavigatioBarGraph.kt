package com.example.shopify.feature.navigation_bar

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.feature.Graph
import com.example.shopify.feature.auth.Auth
import com.example.shopify.feature.auth.authGraph
import com.example.shopify.feature.navigation_bar.cart.cartNavigation
import com.example.shopify.feature.navigation_bar.category.categoryGraph
import com.example.shopify.feature.navigation_bar.home.screen.homeGraph
import com.example.shopify.feature.navigation_bar.my_account.myAccountGraph
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.orderGraph
import com.example.shopify.feature.navigation_bar.productDetails.ProductDetailsGraph
import com.example.shopify.feature.navigation_bar.productDetails.productDetailsGraph
import com.example.shopify.feature.search.view.SearchScreen
import com.example.shopify.feature.wishList.wishListGraph
import com.example.shopify.helpers.firestore.mapper.encodeProductId

@Composable
fun ColumnScope.NavigationBarGraph(
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.weight(1f),
        navController = navController,
        route = Graph.ROOT,
        startDestination = NavigationBarGraph.HOME
    ) {
        myAccountGraph(paddingValues, navController)
        authGraph(navController)
        homeGraph(navController, paddingValues)
        categoryGraph(paddingValues, navController)
        cartNavigation(navController)
        wishListGraph(navController)
        orderGraph(navController)
        productDetailsGraph(navController)
        composable(route = NavigationBarGraph.SEARCH) {
            SearchScreen(
                viewModel = hiltViewModel(),
                navigateToProductDetails = { productId ->
                    navController.navigate("${ProductDetailsGraph.PRODUCT_DETAILS}/${productId.encodeProductId()}")
                },
                navigateToAuth = {navController.navigate(Auth.SIGN_IN)},
                back = { navController.popBackStack() }
            )
        }
    }
}

object NavigationBarGraph {
    const val HOME = "HOME_GRAPH"
    const val CATEGORY = "CATEGORY_GRAPH"
    const val MY_ACCOUNT = "MY_ACCOUNT_GRAPH"
    const val CART = "CART_GRAPH"
    const val SEARCH = "SEARCH"
}