package com.example.shopify.feature.navigation_bar.category.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.feature.navigation_bar.home.screen.home.ImageFromUrl
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.theme.shopifyColors
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

@Composable
fun CategoryProductCard(product: BrandProduct, onItemClick: (ID) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable { onItemClick(product.id) },
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(MaterialTheme.shopifyColors.LightServer)
                    .padding(20.dp),
            ) {
                ImageFromUrl(url = product.images[0])
            }
            Text(
                product.title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp, start = 20.dp, end = 20.dp)
            )
        }
    }
}

@Preview
@Composable
fun previewCategoryCard() {
    ShopifyTheme() {
        CategoryProductCard(product = BrandProduct(
            id = ID(""),
            title = "Ultima show Running Shoes Pink",
            images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
            isFavourite = false,
            price = Storefront.MoneyV2().setAmount("200")
                .setCurrencyCode(Storefront.CurrencyCode.SDG)
        ),
            onItemClick = {})
    }
}