package com.example.shopify.helpers

import androidx.navigation.NamedNavArgument

data class GraphRouteWithArgs(
    val route: String,
    val routeWithArgs: String,
    val args: List<NamedNavArgument>,
    val routeToBeFormatted: String
) {
    fun withArgs(vararg args: Any) = String.format(routeToBeFormatted, *args)
}