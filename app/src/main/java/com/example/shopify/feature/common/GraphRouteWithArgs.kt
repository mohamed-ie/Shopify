package com.example.shopify.feature.common

import androidx.navigation.NamedNavArgument

data class GraphRouteWithArgs(
    val route: String,
    val routeWithArgs: String,
    val args: List<NamedNavArgument>,
    val routeToBeFormatted: String
) {
    fun withArgs(vararg args: String) = String.format(routeToBeFormatted, *args)
}