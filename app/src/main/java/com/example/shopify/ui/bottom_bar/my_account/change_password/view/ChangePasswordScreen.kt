package com.example.shopify.ui.bottom_bar.my_account.change_password.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.my_account.change_password.ChangePasswordViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel,
    back: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.back.onEach {
            Toast.makeText(context, R.string.password_changed, Toast.LENGTH_SHORT).show()
            back()
        }.launchIn(this)
    }

    ChangePasswordScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        back = back
    )
}