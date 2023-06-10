package com.example.shopify.feature.navigation_bar.my_account.screens.addresses.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.R
import com.example.shopify.feature.common.ConfirmationDialog
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.my_account.MyAccountGraph
import com.example.shopify.feature.navigation_bar.my_account.screens.addresses.AddressesViewModel

@Composable
fun AddressesScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: AddressesViewModel,
    back: () -> Unit,
    navigateTo: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    LaunchedEffect(lifecycleOwner) {
        viewModel.loadAddresses()
    }

    when (screenState) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> AddressesScreenContent(
            navigateTo = navigateTo,
            back = back,
            onEvent = viewModel::onEvent,
            addresses = state.addresses
        )

        ScreenState.ERROR -> navigateTo(MyAccountGraph.ERROR)
    }

    ConfirmationDialog(
        visible = state.isDeleteDialogVisible,
        message = stringResource(id = R.string.are_you_sure_you_want_to_delete_this_address),
        onDismiss = { viewModel.onEvent(AddressesEvent.ToggleDeleteConfirmationDialogVisibility(null)) },
        onConfirm = { viewModel.onEvent(AddressesEvent.DeleteAddress) }
    )
}