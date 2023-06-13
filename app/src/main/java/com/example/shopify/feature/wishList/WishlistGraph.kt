package com.example.shopify.feature.wishList

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.productDetails.ProductDetailsGraph
import com.example.shopify.feature.wishList.view.WishListScreen
import com.example.shopify.helpers.firestore.mapper.encodeProductId


fun NavGraphBuilder.wishListGraph(navController: NavController) {
    navigation(
        route = Graph.WISH_LIST,
        startDestination = WishListGraph.WISHLIST
    ){
        composable(route = WishListGraph.WISHLIST) {
            WishListScreen(
                viewModel = hiltViewModel(),
                back = { navController.popBackStack() },
                navigateToProductDetails = { productId -> navController.navigate("${ProductDetailsGraph.PRODUCT_DETAILS}/${productId.encodeProductId()}") })
        }
    }
}

object WishListGraph {
    const val WISHLIST = "WISHLIST"
}