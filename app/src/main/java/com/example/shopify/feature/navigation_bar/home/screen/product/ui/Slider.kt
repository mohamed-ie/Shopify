package com.example.shopify.feature.navigation_bar.home.screen.product.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.theme.ShopifyTheme

@Composable
fun Slider(minValue: Float, maxValue: Float, value: Float, onValueChange: (Float) -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Price")
        Spacer(modifier = Modifier.width(16.dp))
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = minValue..maxValue,
            modifier = Modifier
                .height(8.dp)
                .weight(1f),
            colors = SliderDefaults.colors(inactiveTrackColor = MaterialTheme.colorScheme.background,  )
        )
        Text(
            text = "${value.toInt()}",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewIndicator() {
    ShopifyTheme {
        Slider(0f, 0f, 0f, {})
    }
}