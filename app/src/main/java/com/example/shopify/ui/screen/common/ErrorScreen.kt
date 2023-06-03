package com.example.shopify.ui.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.theme.ShopifyTheme

@Composable
fun ErrorScreen(retry: () -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .aspectRatio(1f),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.error),
            contentDescription = null
        )

        Text(
            text = stringResource(id = R.string.something_went_wrong),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = stringResource(id = R.string.this_doesnt_seem_right_let_us_try_to_sort_it_out),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            shape = MaterialTheme.shapes.small,
            onClick = retry
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
        Spacer(modifier = Modifier.weight(2f))

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorScreen() {
    ShopifyTheme {
        ErrorScreen {}
    }
}