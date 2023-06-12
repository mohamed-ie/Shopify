package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.R
import com.example.shopify.theme.Gray
import com.example.shopify.theme.shopifyColors
import com.example.shopify.utils.shopifyLoading

@Composable
fun ProductDetailsCard(
    vendor:String,
    title:String,
    thumbnail:String,
    currencyCode:String,
    price:String,
    realPrice:String,
    discountPercent:String,
    quantity:String,
    isLowStock:Boolean,
    isFavourite:Boolean,
    onFavouriteClick:(Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = vendor,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.aspectRatio(1f, false),
                contentScale = ContentScale.Inside,
                model = thumbnail,
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
            IconButton(
                onClick = { onFavouriteClick(isFavourite) },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .shadow(5.dp, shape = CircleShape, spotColor = Color.Black)
                    .clip(CircleShape)
                    .size(35.dp)
                    .background(Color.White)

            ) {
                if (isFavourite) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                else{
                    Icon(
                        imageVector =  Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currencyCode,
                        color = Color.Gray,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = price,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(R.string.inclusive_of_vat),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = realPrice,
                        color = Gray,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.LineThrough,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.discount, "$discountPercent%"),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.shopifyColors.DarkGreenColor,
                        fontWeight = FontWeight.Medium
                    )
                }
                if (isLowStock){
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.low_stock_only_4_left,quantity),
                        color = Color.Red,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Normal,
                    )
                }

            }

            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, bottomEnd = 15.dp),
                    modifier = Modifier.height(25.dp),
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.padding(bottom = 3.dp),
                        fontStyle = FontStyle.Italic

                    )
                }
            }
        }


    }
}


@Preview
@Composable
fun PreviewProductDetailsCard() {
    ProductDetailsCard(
        vendor = "Adidas",
        title = "Ultima show Running Shoes Pink",
        thumbnail = "https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800",
        currencyCode = "AED",
        price = "172.00",
        realPrice = "249.00",
        discountPercent = "30%",
        quantity = "4",
        isLowStock = false,
        isFavourite = true,
    ){

    }
}