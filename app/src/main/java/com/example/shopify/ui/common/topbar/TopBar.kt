package com.example.shopify.ui.common.topbar

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopify.R
import com.example.shopify.ui.theme.ShopifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamedTopAppBar(
    title: String = stringResource(id = R.string.app_name),
    back: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.background(Color.White),
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = back) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    )
}



@Preview
@Composable
fun PreviewNamedTopAppBar() {
    ShopifyTheme {
        NamedTopAppBar {}
    }
}
