package com.example.shopify.ui.bottom_bar.my_account.profile.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.ui.common.screen.ErrorScreen
import com.example.shopify.ui.common.screen.LoadingScreen
import com.example.shopify.ui.common.state.ScreenState
import com.example.shopify.ui.bottom_bar.my_account.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: ProfileViewModel,
    navigateTo: (String) -> Unit,
    back: () -> Unit
) {

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

    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> ProfileScreenContent(
            state = viewModel.state.collectAsState().value,
            navigateTo = navigateTo,
            back = back
        )

        ScreenState.ERROR -> ErrorScreen {viewModel.loadMinCustomerInfo()}
    }

}