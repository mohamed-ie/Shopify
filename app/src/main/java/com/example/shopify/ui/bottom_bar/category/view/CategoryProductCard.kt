package com.example.shopify.ui.bottom_bar.category.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.ui.bottom_bar.home.product.model.BrandProduct
import com.example.shopify.ui.theme.ShopifyTheme
import com.example.shopify.utils.shopifyLoading
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

@Composable
fun CategoryProductCard(product: BrandProduct, onItemClick: (ID) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onItemClick(product.id) },
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .background(Color.White)
                    .padding(vertical = 8.dp)
                    .aspectRatio(1.5f),
                model = product.images[0],
                contentScale = ContentScale.Fit,
                contentDescription = null,
                loading = {
                    Box(
                        modifier = Modifier
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
                })
            Text(
                product.title,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCategoryCard() {
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