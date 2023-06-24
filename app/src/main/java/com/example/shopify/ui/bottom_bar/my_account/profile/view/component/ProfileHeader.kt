package com.example.shopify.ui.bottom_bar.my_account.profile.view.component

import androidx.compose.foundation.layout.Column
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
import com.example.shopify.ui.theme.ShopifyTheme


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
                .padding(top = 24.dp, bottom = 12.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = email
            )

            TextButton(onClick = onEditInfoClick, shape = RectangleShape) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.edit_info))
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