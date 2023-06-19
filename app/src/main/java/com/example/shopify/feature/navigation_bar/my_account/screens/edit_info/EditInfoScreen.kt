package com.example.shopify.feature.navigation_bar.my_account.screens.edit_info

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.shopify.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun EditInfoScreen(
    viewModel: EditInfoViewModel,
    back: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.back.onEach {
            Toast.makeText(context, R.string.name_changed, Toast.LENGTH_SHORT).show()
            back()
        }.launchIn(this)
    }

    EditInfoScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        back = back
    )
}