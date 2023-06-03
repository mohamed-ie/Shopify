package com.example.shopify.ui.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.ui.navigation.Graph
import com.example.shopify.ui.navigation.Landing
import com.example.shopify.ui.screen.splash.ui.SplashScreen


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