package com.example.shopify.feature.navigation_bar.my_account.screens.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.common.component.RemoteErrorHeader
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.utils.shopifyLoading

@Composable
fun ChangePasswordScreenContent(
    state: ChangePasswordState, onEvent: (ChangePasswordEvent) -> Unit, back: () -> Unit
) {
    val confirmPasswordState = state.confirmPassword
    val passwordState = state.password

    Column(modifier = Modifier.fillMaxSize()) {
        NamedTopAppBar(
            stringResource(id = R.string.change_password), back = back
        )
        RemoteErrorHeader(error = state.remoteError?.asString())
        Spacer(modifier = Modifier.height(16.dp))

       val focusManager = LocalFocusManager.current

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = passwordState.value,
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            onValueChange = { onEvent(ChangePasswordEvent.PasswordChanged(it)) },
            label = { Text(text = stringResource(id = R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
                    visualTransformation = if (passwordState.triallingIconState) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { onEvent(ChangePasswordEvent.TogglePasswordVisibility) }) {
                    Icon(
                        imageVector = if (passwordState.triallingIconState)
                            Icons.Rounded.Visibility
                        else
                            Icons.Rounded.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = confirmPasswordState.value,
            onValueChange = { onEvent(ChangePasswordEvent.ConfirmPasswordChanged(it)) },
            label = { Text(text = stringResource(id = R.string.confirm_password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (confirmPasswordState.triallingIconState) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { onEvent(ChangePasswordEvent.ToggleConfirmPasswordVisibility) }) {
                    Icon(
                        imageVector = if (confirmPasswordState.triallingIconState)
                            Icons.Rounded.Visibility
                        else
                            Icons.Rounded.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
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
            onClick = { onEvent(ChangePasswordEvent.Change) },
            shape = MaterialTheme.shapes.small,
            enabled = !state.isLoading
        ) {
            Text(
                modifier = Modifier.shopifyLoading(state.isLoading),
                text = stringResource(id = R.string.change_password)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPasswordScreenContent() {
    ShopifyTheme {
        ChangePasswordScreenContent(ChangePasswordState(), {}, {})
    }
}