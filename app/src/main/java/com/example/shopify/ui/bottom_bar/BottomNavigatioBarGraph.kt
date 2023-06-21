package com.example.shopify.ui.bottom_bar

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopify.helpers.firestore.mapper.encodeProductId
import com.example.shopify.ui.Graph
import com.example.shopify.ui.address.addressGraph
import com.example.shopify.ui.auth.Auth
import com.example.shopify.ui.auth.authGraph
import com.example.shopify.ui.bottom_bar.category.categoryGraph
import com.example.shopify.ui.bottom_bar.home.homeGraph
import com.example.shopify.ui.bottom_bar.my_account.myAccountGraph
import com.example.shopify.ui.bottom_bar.cart.cartNavigation
import com.example.shopify.ui.order.orderGraph
import com.example.shopify.ui.product_details.productDetailsGraph
import com.example.shopify.ui.search.view.SearchScreen
import com.example.shopify.ui.wishList.wishListGraph

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
        addressGraph(navController)
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
                    navController.navigate("${com.example.shopify.ui.product_details.ProductDetailsGraph.PRODUCT_DETAILS}/${productId.encodeProductId()}")
                },
                navigateToAuth = { navController.navigate(Auth.SIGN_IN) },
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