package com.example.shopify.feature.address.add_address.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.address.AddressViewModel
import com.example.shopify.feature.address.add_address.AddAddressViewModel
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AddAddressScreen(
    viewModel: AddAddressViewModel,
    addressViewModel: AddressViewModel,
    back: () -> Unit
) {
    val address by addressViewModel.address
        .collectAsState()

    LaunchedEffect(key1 = address , block = {
        address?.let { viewModel.loadAddress(it) }
    })

    LaunchedEffect(key1 = Unit) {
        viewModel.back.onEach {
            back()
        }.launchIn(this)
    }


    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()

        ScreenState.STABLE -> AddAddressScreenContent(
            state = viewModel.state.collectAsState().value,
            onEvent = viewModel::onEvent,
            back = back
        )

        ScreenState.ERROR ->
            ErrorScreen(retry = { viewModel.onEvent(AddAddressEvent.Save) })
    }
}