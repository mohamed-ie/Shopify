package com.example.shopify.feature.address.add_address.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.address.add_address.AddAddressViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AddAddressScreen(
    viewModel: AddAddressViewModel,
    back: () -> Unit
) {
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