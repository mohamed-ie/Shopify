package com.example.shopify.feature.navigation_bar

import android.support.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

open class NavigationBarScreen(
    val route: String,
    @StringRes val name: Int,
    val icon: ImageVector
)

//sealed class NavigationBarScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
//    object Home :
//        NavigationBarScreen(route = "home", resourceId = R.string.home, icon = Icons.Rounded.Home)
//
//    object Category : NavigationBarScreen(
//        route = "category", resourceId = R.string.category,
//        icon = Icons.Rounded.Category
//    )
//
//    object Favourite : NavigationBarScreen(
//        route = "favourite", resourceId = R.string.favourite,
//        icon = Icons.Rounded.Favorite
//    )
//
//    object Me : NavigationBarScreen(
//        route = "me", resourceId = R.string.me,
//        icon = Icons.Rounded.Person
//    )
//
//    object Cart : NavigationBarScreen(
//        route = "cart", resourceId = R.string.cart,
//        icon = Icons.Rounded.ShoppingCart
//    )
//}
