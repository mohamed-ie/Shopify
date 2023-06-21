package com.example.shopify.ui.order.orders.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.R
import com.example.shopify.model.cart.order.LineItems
import com.example.shopify.ui.theme.Gray
import com.example.shopify.ui.theme.ShopifyTheme
import com.example.shopify.utils.shopifyLoading
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID


@Composable
fun OrderDetailsProducts(lineItems: LineItems) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .aspectRatio(1f, true)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                model = lineItems.thumbnail,
                contentDescription = null,
                loading = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .shopifyLoading()
                    )
                },
                error = {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Rounded.BrokenImage,
                        tint = Color.Gray,
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    text = lineItems.collection,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray
                )
                Text(
                    text = lineItems.name,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${lineItems.price.currencyCode} ${lineItems.price.amount}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.sold_by))
                        append(" ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(lineItems.vendor)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderDetailsProduct() {
    ShopifyTheme() {
        OrderDetailsProducts(
            lineItems = LineItems(
                id = ID("gid://shopify/Product/8312391237939"),
                name = "VANS | AUTHENTIC | (MULTI EYELETS) | GRADIENT/CRIMSON",
                thumbnail = "https://cdn.shopify.com/s/files/1/0774/1662/8531/products/d841f71ea6845bf6005453e15a18c632.jpg?v=1685530920",
                collection = "SHOES",
                vendor = "VANS",
                price = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                description = "The forefather of the Vans family, the Vans Authentic was introduced in 1966 and nearly 4 decades later is still going strong, its popularity extending from the original fans - skaters and surfers to all sorts. The Vans Authentic is constructed from canvas and Vans' signature waffle outsole construction."
            )
        )
    }

}
