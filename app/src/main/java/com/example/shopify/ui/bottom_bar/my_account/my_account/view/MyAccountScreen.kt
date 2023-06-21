package com.example.shopify.ui.bottom_bar.my_account.my_account.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.R
import com.example.shopify.ui.common.ConfirmationDialog
import com.example.shopify.ui.common.screen.ErrorScreen
import com.example.shopify.ui.common.screen.LoadingScreen
import com.example.shopify.ui.common.component.RadioGroupModalBottomSheet
import com.example.shopify.ui.common.state.ScreenState
import com.example.shopify.ui.bottom_bar.my_account.my_account.MyAccountViewModel
import com.shopify.buy3.Storefront.CurrencyCode

@Composable
fun MyAccountScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    innerPadding: PaddingValues,
    viewModel: MyAccountViewModel,
    navigateTo: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val isSignedIn by viewModel.isSignedIn.collectAsState(false)
    val screenState by viewModel.screenState.collectAsState()

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadMinCustomerInfo()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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


        ScreenState.ERROR -> ErrorScreen { viewModel.loadMinCustomerInfo() }
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