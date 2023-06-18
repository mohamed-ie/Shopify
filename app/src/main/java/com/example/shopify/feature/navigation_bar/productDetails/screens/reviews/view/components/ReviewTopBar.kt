package com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.feature.navigation_bar.common.HomeTopBar
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Discount
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Price
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.VariantItem
import com.example.shopify.utils.shopifyLoading


@Composable
fun ReviewTopBar(
    product: Product,
    back:()->Unit
) {
    HomeTopBar(back = back) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp).offset(y = (-7).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .height(70.dp)
                    .aspectRatio(1f)
                    .padding(start = 15.dp),
                contentScale = ContentScale.Inside,
                model = product.images[0],
                contentDescription = null,
                loading = {
                    Box(
                        Modifier
                            .fillMaxWidth()
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
            Spacer(modifier = Modifier.width(10.dp))
            Column (modifier = Modifier.weight(1f).padding(end = 10.dp)){
                Text(
                    text = product.vendor,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black
                )
            }
        }
    }
}


@Preview
@Composable
fun ReviewTopBarPreview() {
    ReviewTopBar(
        product = Product(
            images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
            description = "The Stan Smith owned the tennis court in the '70s." +
                    " Today it runs the streets with the same clean," +
                    " classic style." +
                    " These kids' shoes preserve the iconic look of the original," +
                    " made in leather with punched 3-Stripes," +
                    " heel and tongue logos and lightweight step-in cushioning.",
            totalInventory = 5,
            variants = listOf(VariantItem("","","white/1")),
            title = "iPhone 14 Pro 256GB Deep Purple 5G With FaceTime - International Version",
            price = Price(
                amount = "172.00",
                currencyCode = "AED"
            ),
            discount = Discount(
                realPrice = "249.00",
                percent = 30
            ),
            vendor = "Adidas",
        )
    ) {}
}