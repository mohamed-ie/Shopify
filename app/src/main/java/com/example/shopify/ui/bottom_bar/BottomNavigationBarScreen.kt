package com.example.shopify.ui.bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopify.ui.bottom_bar.category.CategoryGraph
import com.example.shopify.ui.bottom_bar.home.HomeGraph
import com.example.shopify.ui.bottom_bar.my_account.MyAccountGraph
import com.example.shopify.ui.bottom_bar.cart.CartGraph
import com.example.shopify.ui.theme.ShopifyTheme


private val items = listOf<NavigationBarScreen>(
    HomeGraph.Home,
    CategoryGraph.Category,
    CartGraph.Cart,
    MyAccountGraph.MyAccount
)

@Composable
fun HomeNavigationBarScreen(navController: NavHostController = rememberNavController()) {
    Column(modifier = Modifier.fillMaxSize()) {
        NavigationBarGraph(navController = navController)
        HomeNavigationBar(navController)
    }
}

@Composable
private fun HomeNavigationBar(navController: NavController = rememberNavController()) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val visible = items.any { it.route == currentDestination?.route }

    AnimatedVisibility(
        visible = visible,
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom),
        enter = expandVertically(expandFrom = Alignment.Bottom)
    ) {
        NavigationBar {
            items.forEach { screen ->
                NavigationItem(screen, navController, currentDestination)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHomeNavigationBar() {
    ShopifyTheme {
        HomeNavigationBar()
    }
}

@Composable
private fun RowScope.NavigationItem(
    screen: NavigationBarScreen,
    navController: NavController,
    currentDestination: NavDestination?
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    NavigationBarItem(
        icon = {
            Icon(
                imageVector = if (selected) screen.selectedIcon else screen.unSelectedIcon,
                contentDescription = null,
                tint = if (selected) MaterialTheme.colorScheme.primary else LocalContentColor.current
            )
        },
        label = { if (selected) Text(stringResource(screen.name)) },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
//                restoreState = true
            }
        }
    )
}