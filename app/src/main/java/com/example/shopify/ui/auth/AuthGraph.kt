package com.example.shopify.ui.auth

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.ui.Graph
import com.example.shopify.ui.auth.login.ui.LoginScreen
import com.example.shopify.ui.auth.registration.ui.SignUpScreen


fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        route = Graph.AUTH,
        startDestination = Auth.SIGN_IN
    ) {
        composable(route = Auth.SIGN_IN) {
            LoginScreen(
                viewModel = hiltViewModel(),
                navigateToSignUp = {
                    navController.navigate(Auth.SIGN_UP) {
                        popUpTo(Graph.AUTH)
                    }
                },
                navigateToHome = navController::popBackStack,
                onCloseScreen = { navController.popBackStack() }
            )
        }

        composable(route = Auth.SIGN_UP) {
            SignUpScreen(
                viewModel = hiltViewModel(),
                navigateToSignIn = {
                    navController.navigate(Auth.SIGN_IN) {
                        popUpTo(Graph.AUTH)
                    }
                },
                onCloseScreen = { navController.popBackStack() }
            )
        }
    }
}

object Auth {
    const val SIGN_IN = "SIGN_IN"
    const val SIGN_UP = "SIGN_UP"
}