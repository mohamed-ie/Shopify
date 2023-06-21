package com.example.shopify.ui.product_details.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.home.product.ui.ImageCardScrollHorizontally

@Composable
fun ProductDetailsCard(
    vendor: String,
    title: String,
    thumbnails: List<String>,
    currencyCode: String,
    price: String,
    quantity: String,
    isAvailable:Boolean,
    isLowStock: Boolean,
    isFavourite: Boolean,
    onFavouriteClick: (Boolean) -> Unit
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
            contentAlignment = Alignment.Center
        ) {
            ImageCardScrollHorizontally(
                isAvailable = isAvailable,
                images = thumbnails,
                isFavourite = isFavourite,
                addToFavourite = { onFavouriteClick(isFavourite) }
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row {
            Column {
                if(isAvailable){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currencyCode,
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
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
                }else{
                    Text(
                        text = stringResource(R.string.the_current_product_is_not_available),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(R.string.please_check_our_other_available_products),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                if (isLowStock && isAvailable) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.low_stock_only_4_left, quantity),
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
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 15.dp
                    ),
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
        thumbnails = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
        currencyCode = "AED",
        price = "172.00",
        quantity = "4",
        isLowStock = false,
        isFavourite = true,
        isAvailable = true
    ) {

    }
}
