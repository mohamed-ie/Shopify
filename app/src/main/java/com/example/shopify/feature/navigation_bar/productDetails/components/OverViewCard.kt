package com.example.shopify.feature.navigation_bar.productDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.theme.shopifyColors

@Composable
fun OverViewCard(
    description:String
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.overview),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
        )
        Divider(color =  MaterialTheme.shopifyColors.ServerColor, thickness = 1.dp)
        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 20.dp, end = 20.dp)
        )
    }
}