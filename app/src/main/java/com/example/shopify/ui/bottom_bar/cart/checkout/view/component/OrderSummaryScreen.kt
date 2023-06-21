package com.example.shopify.ui.bottom_bar.cart.checkout.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.ui.theme.ShopifyTheme

@Composable
fun OrderSummaryScreen(
    itemsCount: Int,
    subtotal: String,
    shippingFees: String,
    discount: String,
    taxes: String,
    total: String,
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Order Summary",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$itemsCount Items",
                style = TextStyle(fontSize = 16.sp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                OrderSummaryRow(label = "Subtotal", value = subtotal)
                OrderSummaryRow(label = "Shipping Fees", value = shippingFees)
                OrderSummaryRow(label = "Discount", value = discount)
                OrderSummaryRow(label = "Taxes", value = taxes)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                OrderSummaryRow(label = "Total", value = total)
            }
        }
    }
}

@Composable
fun OrderSummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = TextStyle(fontSize = 16.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderSummaryScreen() {
    ShopifyTheme {
        OrderSummaryScreen(0, "", "", "", "", "")
    }
}