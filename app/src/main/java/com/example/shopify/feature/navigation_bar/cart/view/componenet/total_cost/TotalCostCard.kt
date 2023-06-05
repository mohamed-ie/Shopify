package com.example.shopify.feature.navigation_bar.cart.view.componenet.total_cost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.cart.view.cartElevation
import com.example.shopify.theme.Gray
import com.example.shopify.theme.Green170
import com.example.shopify.theme.ShopifyTheme

@Composable
fun TotalCostCard(
    itemsCount: Int,
    subTotalsPrice: String,
    shippingFee: String,
    taxes: String,
    totalPrice: String
) {
    ElevatedCard(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(cartElevation),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TotalCostItem(
                title = buildAnnotatedString {
                    append(stringResource(id = R.string.subtotal))
                    withStyle(style = SpanStyle(color = Gray)) {
                        append(" (")
                        append(
                            if (itemsCount == 1)
                                stringResource(id = R.string.one_item)
                            else
                                stringResource(id = R.string.number_of_items, itemsCount)
                        )
                        append(")")
                    }
                },
                value = subTotalsPrice
            )

            TotalCostItem(title = stringResource(id = R.string.shipping_fee), value = shippingFee)

            TotalCostItem(title = stringResource(id = R.string.taxes), value = taxes)

            Divider(Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.total),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = totalPrice,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun TotalCostItem(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
        )

        val free = value == "FREE"
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (free) FontWeight.Bold else FontWeight.Normal,
            color = if (free) Green170 else Color.Unspecified
        )

    }
}
@Composable
private fun TotalCostItem(title: AnnotatedString, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
        )

        val free = value == "FREE"
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (free) FontWeight.Bold else FontWeight.Normal,
            color = if (free) Green170 else Color.Unspecified
        )

    }
}

@Preview
@Composable
fun PreviewTotalCostCard() {
    ShopifyTheme {
        TotalCostCard(5, "EGP 915.00", "EGP 20.00", "EGP 100.50", "EGP 1035.50")
    }
}

@Preview
@Composable
fun PreviewTotalCostCardFreeShippingFee() {
    ShopifyTheme {
        TotalCostCard(5, "EGP 915.00", "FREE", "EGP 100.50", "EGP 1015.50")
    }
}