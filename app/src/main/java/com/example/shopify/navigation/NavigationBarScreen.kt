package com.example.shopify.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.shopify.R

sealed class NavigationBarScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector){
    object Home:NavigationBarScreen(route = "home", resourceId = R.string.home,icon = Icons.Rounded.Home)
}
