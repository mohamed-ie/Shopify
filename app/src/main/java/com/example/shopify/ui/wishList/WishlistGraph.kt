package com.example.shopify.ui.wishList

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.shopify.helpers.firestore.mapper.encodeProductId
import com.example.shopify.ui.Graph
import com.example.shopify.ui.wishList.view.WishListScreen


fun NavGraphBuilder.wishListGraph(navController: NavController) {
    navigation(
        route = Graph.WISH_LIST,
        startDestination = WishListGraph.WISHLIST
    ){
        composable(route = WishListGraph.WISHLIST) {
            WishListScreen(
                viewModel = hiltViewModel(),
                back = { navController.popBackStack() },
                navigateToProductDetails = { productId -> navController.navigate("${com.example.shopify.ui.product_details.ProductDetailsGraph.PRODUCT_DETAILS}/${productId.encodeProductId()}") })
        }
    }
}

object WishListGraph {
    const val WISHLIST = "WISHLIST"
}