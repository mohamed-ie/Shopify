package com.example.shopify.feature.landing

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.feature.Graph
import com.example.shopify.feature.landing.splash.ui.SplashScreen


fun NavGraphBuilder.landingGraph(navController: NavController){
    navigation(
        route = Graph.LANDING,
        startDestination = Landing.SPLASH
    ){
        composable(route = Landing.SPLASH){
            SplashScreen(
                viewModel = hiltViewModel(),
                navigateToHome = { navController.navigate(Graph.HOME) })
            {
               navController.navigate(Graph.AUTH)
            }
        }
    }
}

object Landing{
    const val SPLASH = "SPLASH"
}