package com.example.shopify.feature.navigation_bar.my_account

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.NavigationBarGraph
import com.example.shopify.feature.navigation_bar.NavigationBarScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.change_password.ChangePasswordScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.change_phone_number.ChangePhoneNumberScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.edit_info.EditInfoScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view.MyAccountScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.profile.ProfileScreen


fun NavGraphBuilder.myAccountGraph(paddingValues: PaddingValues, navController: NavHostController) {
    navigation(
        route = NavigationBarGraph.MY_ACCOUNT,
        startDestination = MyAccountGraph.MyAccount.route
    ) {
        composable(route = MyAccountGraph.MyAccount.route) {
            MyAccountScreen(
                innerPadding = paddingValues,
                viewModel = hiltViewModel(),
                navigateTo = navController::navigate
            )
        }

        composable(route = MyAccountGraph.PROFILE) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                navigateTo = navController::navigate,
                back = navController::popBackStack
            )
        }

        composable(route = MyAccountGraph.EDIT_INFO) {
            EditInfoScreen(
                viewModel = hiltViewModel(),
                back = navController::popBackStack
            )
        }

        composable(route = MyAccountGraph.CHANGE_PHONE_NUMBER) {
            ChangePhoneNumberScreen(
                viewModel = hiltViewModel(),
                back = navController::popBackStack
            )
        }

        composable(route = MyAccountGraph.CHANGE_PASSWORD) {
            ChangePasswordScreen(
                viewModel = hiltViewModel(),
                back = navController::popBackStack
            )
        }
    }
}


object MyAccountGraph {
    object MyAccount : NavigationBarScreen(
        route = "MY_ACCOUNT",
        name = R.string.my_account,
        icon = Icons.Rounded.Person
    )

    const val CHANGE_PASSWORD = "CHANGE_PASSWORD"
    const val CHANGE_PHONE_NUMBER = "CHANGE_PHONE_NUMBER"
    const val ADD_ADDRESS = "ADD_ADDRESS_1"

    const val PROFILE = "PROFILE"
    const val EDIT_INFO = "EDIT_INFO"
}