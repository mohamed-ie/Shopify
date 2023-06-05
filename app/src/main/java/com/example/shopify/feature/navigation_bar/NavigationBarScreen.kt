package com.example.shopify.feature.navigation_bar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.shopify.R

sealed class NavigationBarScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home :
        NavigationBarScreen(route = "home", resourceId = R.string.home, icon = Icons.Rounded.Home)

    object Category : NavigationBarScreen(
        route = "category", resourceId = R.string.category,
        icon = Icons.Rounded.Category
    )

    object Favourite : NavigationBarScreen(
        route = "favourite", resourceId = R.string.favourite,
        icon = Icons.Rounded.Favorite
    )

    object Me : NavigationBarScreen(
        route = "me", resourceId = R.string.me,
        icon = Icons.Rounded.Person
    )

    object Cart : NavigationBarScreen(
        route = "cart", resourceId = R.string.cart,
        icon = Icons.Rounded.ShoppingCart
    )
}
