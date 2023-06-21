package com.example.shopify.ui.bottom_bar.my_account

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.twotone.Person
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.NavigationBarGraph
import com.example.shopify.ui.bottom_bar.NavigationBarScreen
import com.example.shopify.ui.bottom_bar.my_account.change_password.view.ChangePasswordScreen
import com.example.shopify.ui.bottom_bar.my_account.change_phone_number.view.ChangePhoneNumberScreen
import com.example.shopify.ui.bottom_bar.my_account.edit_info.view.EditInfoScreen
import com.example.shopify.ui.bottom_bar.my_account.my_account.view.MyAccountScreen
import com.example.shopify.ui.bottom_bar.my_account.profile.view.ProfileScreen


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
        unSelectedIcon = Icons.TwoTone.Person,
        selectedIcon = Icons.Outlined.Person
    )

    const val CHANGE_PASSWORD = "CHANGE_PASSWORD"
    const val CHANGE_PHONE_NUMBER = "CHANGE_PHONE_NUMBER"
    const val PROFILE = "PROFILE"
    const val EDIT_INFO = "EDIT_INFO"
}