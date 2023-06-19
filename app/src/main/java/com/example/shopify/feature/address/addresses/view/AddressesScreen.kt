package com.example.shopify.feature.address.addresses.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.R
import com.example.shopify.feature.address.addresses.AddressesViewModel
import com.example.shopify.feature.navigation_bar.common.ConfirmationDialog
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AddressesScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    pickShipping: Boolean = false,
    pickBilling: Boolean = false,
    viewModel: AddressesViewModel,
    back: () -> Unit,
    navigateTo: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    LaunchedEffect(lifecycleOwner) {
        viewModel.loadAddresses()
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.back.onEach {
            back()
        }.launchIn(this)
    })

    when (screenState) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> AddressesScreenContent(
            navigateTo = navigateTo,
            allowPick = pickShipping||pickBilling,
            back = back,
            onEvent = viewModel::onEvent,
            addresses = state.addresses
        )

        ScreenState.ERROR -> ErrorScreen {viewModel.loadAddresses()}
    }

    ConfirmationDialog(
        visible = state.isDeleteDialogVisible,
        message = stringResource(id = R.string.are_you_sure_you_want_to_delete_this_address),
        onDismiss = { viewModel.onEvent(AddressesEvent.ToggleDeleteConfirmationDialogVisibility(null)) },
        onConfirm = { viewModel.onEvent(AddressesEvent.DeleteAddress) }
    )
}