package com.example.shopify.ui.bottom_bar.my_account.change_phone_number.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.my_account.change_phone_number.ChangePhoneNumberViewModel
import com.example.shopify.ui.theme.ShopifyTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ChangePhoneNumberScreen(viewModel: ChangePhoneNumberViewModel, back: () -> Unit) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.back.onEach {
            Toast.makeText(context, R.string.phone_number_changed, Toast.LENGTH_SHORT).show()
            back()
        }.launchIn(this)
    }

    ChangePhoneNumberScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        back = back
    )
}

@Preview
@Composable
fun PreviewChangePhoneNumberScreen() {
    ShopifyTheme {
    }
}
