package com.example.shopify.feature.navigation_bar.my_account.screens.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.theme.ShopifyTheme


@Composable
fun ProfileHeader(name: String, email: String, onEditInfoClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = email)

            TextButton(
                contentPadding = PaddingValues(),
                onClick = onEditInfoClick
            ) {
                Text(text = stringResource(id = R.string.edit_info))
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileHeader() {
    ShopifyTheme {
        ProfileHeader("Mohamed Ibrahim", "mohammedie98@gmail.com") {}
    }
}