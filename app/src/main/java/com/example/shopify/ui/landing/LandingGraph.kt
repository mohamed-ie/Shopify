package com.example.shopify.ui.landing

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.ui.Graph
import com.example.shopify.ui.landing.splash.ui.SplashScreen


fun NavGraphBuilder.landingGraph(navController: NavController){
    navigation(
        route = Graph.LANDING,
        startDestination = Landing.SPLASH
    ){
        composable(route = Landing.SPLASH){
            SplashScreen(navigateToHome = { navController.navigate(Graph.HOME) })
        }
    }
}

object Landing{
    const val SPLASH = "SPLASH"
}