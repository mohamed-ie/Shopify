package com.example.shopify.feature.navigation_bar.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.twotone.Category
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.category.view.CategoryScreen
import com.example.shopify.helpers.firestore.mapper.encodeProductId

fun NavGraphBuilder.categoryGraph(paddingValues: PaddingValues, navController: NavHostController) {
    navigation(
        route = NavigationBarGraph.CATEGORY,
        startDestination = CategoryGraph.Category.route
    ) {
        composable(route = CategoryGraph.Category.route) {
            CategoryScreen(
                viewModel = hiltViewModel(),
                paddingValues = paddingValues,
                navigateTo = { productId ->
                    navController.navigate("${CategoryGraph.PRODUCT_DETAILS}/${productId.encodeProductId()}")
                },
                navigateToSearch = {
                    navController.navigate(NavigationBarGraph.SEARCH)
                }
            )
        }

    }
}


object CategoryGraph {
    object Category : NavigationBarScreen(
        route = "CATEGORY",
        name = R.string.category,
        unSelectedIcon = Icons.TwoTone.Category,
        selectedIcon = Icons.Outlined.Category
    )

    const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
}