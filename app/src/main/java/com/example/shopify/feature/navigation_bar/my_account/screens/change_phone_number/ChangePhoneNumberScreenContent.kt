package com.example.shopify.feature.navigation_bar.my_account.screens.change_phone_number

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.common.component.RemoteErrorHeader
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.utils.shopifyLoading

@Composable
fun ChangePhoneNumberScreenContent(
    state: ChangePhoneNumberState,
    onEvent: (ChangePhoneNumberEvent) -> Unit,
    back: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        NamedTopAppBar(
            stringResource(id = R.string.change_password),
            back = back
        )
        RemoteErrorHeader(error = state.remoteError?.asString())
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = state.phone.value,
            onValueChange = { onEvent(ChangePhoneNumberEvent.PhoneChanged(it)) },
            label = { Text(text = stringResource(id = R.string.phone_number)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            onClick = { onEvent(ChangePhoneNumberEvent.Change) },
            shape = MaterialTheme.shapes.small,
            enabled = !state.isLoading
        ) {
            Text(
                modifier = Modifier.shopifyLoading(state.isLoading),
                text = stringResource(id = R.string.change_phone_number)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePhoneNumberScreenContent() {
    ShopifyTheme {
        ChangePhoneNumberScreenContent(ChangePhoneNumberState(), {}, {})
    }
}