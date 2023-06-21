package com.example.shopify.ui.bottom_bar.cart.checkout.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.ui.theme.ShopifyTheme

@Composable
fun CheckoutFooterScreen(
    totalItems: Int,
    totalPrice: String,
    onPlaceOrderClick: () -> Unit
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier.padding(top = 2.dp),
        elevation = CardDefaults.cardElevation(.5.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$totalItems Items",
                    style = TextStyle(fontSize = 16.sp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = totalPrice,
                    style = TextStyle(fontSize = 16.sp),
                    fontWeight = FontWeight.Bold

                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onPlaceOrderClick,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "PLACE ORDER",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckOutFooterScreen() {
    ShopifyTheme {
        CheckoutFooterScreen(totalItems = 5, totalPrice = "EGY 55.5") {
        }
    }
}