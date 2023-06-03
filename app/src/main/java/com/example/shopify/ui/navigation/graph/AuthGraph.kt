package com.example.shopify.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.ui.navigation.Auth
import com.example.shopify.ui.navigation.Graph
import com.example.shopify.ui.screen.auth.login.ui.LoginScreen
import com.example.shopify.ui.screen.auth.registration.ui.SignUpScreen



fun NavGraphBuilder.authGraph(navController:NavController) {
    navigation(
        route = Graph.AUTH,
        startDestination = Auth.SIGN_IN
    ){
        composable(route = Auth.SIGN_IN){
            LoginScreen(viewModel = hiltViewModel(), navigateToSignUp = {
                navController.navigate(Auth.SIGN_UP)
            }){
                navController.navigate(Graph.HOME)
            }
        }

        composable(route = Auth.SIGN_UP){
            SignUpScreen(viewModel = hiltViewModel()) {
                navController.navigate(Auth.SIGN_IN)
            }
        }
    }
}