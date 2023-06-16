package com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.ConfirmationDialog
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.RadioGroupModalBottomSheet
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.my_account.MyAccountGraph
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.MyAccountViewModel
import com.shopify.buy3.Storefront.CurrencyCode

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun MyAccountScreen(
    innerPadding: PaddingValues,
    viewModel: MyAccountViewModel,
    navigateTo: (String) -> Unit,
    back: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val isSignedIn by viewModel.isSignedIn.collectAsState(false)
    val screenState by viewModel.screenState.collectAsState()

    when (screenState) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> if (isSignedIn)
            MyAccountSignedInScreenContent(
                innerPadding = innerPadding,
                state = state,
                onEvent = viewModel::onEvent,
                navigateTo = navigateTo
            ) else
            MyAccountScreenContent(
                state = state,
                onEvent = viewModel::onEvent,
                innerPadding = innerPadding,
                navigateTo = navigateTo
            )


        ScreenState.ERROR -> navigateTo(MyAccountGraph.ERROR)
    }

    ConfirmationDialog(
        visible = state.isSignOutDialogVisible,
        message = stringResource(id = R.string.are_you_sure_you_want_to_sign_out),
        onDismiss = { viewModel.onEvent(MyAccountEvent.ToggleSignOutConfirmDialogVisibility) },
        onConfirm = { viewModel.onEvent(MyAccountEvent.SignOut) })

    RadioGroupModalBottomSheet(
        visible = state.isRadioGroupModalBottomSheetVisible,
        title = stringResource(id = R.string.select_currency),
        options = CurrencyCode.values().map { it.name },
        selected = state.currency,
        onOptionSelected = { viewModel.onEvent(MyAccountEvent.CurrencyChanged(it)) },
        onDismiss = { viewModel.onEvent(MyAccountEvent.ToggleCurrencyRadioGroupModalSheetVisibility) }
    )
}