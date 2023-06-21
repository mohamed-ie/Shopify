package com.example.shopify.ui.bottom_bar.my_account.edit_info.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.common.topbar.NamedTopAppBar
import com.example.shopify.ui.common.component.RemoteErrorHeader
import com.example.shopify.ui.theme.ShopifyTheme
import com.example.shopify.utils.shopifyLoading

@Composable
fun EditInfoScreenContent(
    state: EditInfoState,
    onEvent: (EditInfoEvent) -> Unit,
    back: () -> Unit
) {
    val firstNameState = state.firstName
    val lastNameState = state.lastName

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)) {
        NamedTopAppBar(
            stringResource(id = R.string.edit_information),
            back = back
        )
        RemoteErrorHeader(error = state.remoteError?.asString())
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = firstNameState.value,
            onValueChange = { onEvent(EditInfoEvent.FirstNameChanged(it)) },
            label = { Text(text = stringResource(id = R.string.first_name)) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = lastNameState.value,
            onValueChange = { onEvent(EditInfoEvent.LastNameChanged(it)) },
            label = { Text(text = stringResource(id = R.string.last_name)) },
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
            onClick = { onEvent(EditInfoEvent.Change) },
            shape = MaterialTheme.shapes.small,
            enabled = !state.isLoading
        ) {
            Text(
                modifier = Modifier.shopifyLoading(state.isLoading),
                text = stringResource(id = R.string.change_name)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditScreenContent() {
    ShopifyTheme {
        EditInfoScreenContent(EditInfoState(),{},{})
    }
}