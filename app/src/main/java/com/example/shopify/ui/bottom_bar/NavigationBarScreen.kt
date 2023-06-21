package com.example.shopify.ui.bottom_bar

import android.support.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

open class NavigationBarScreen(
    val route: String,
    @StringRes val name: Int,
    val unSelectedIcon: ImageVector,
    val selectedIcon : ImageVector
)