package com.example.shopify.ui.wishList.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.R
import com.example.shopify.ui.common.component.ShopifyOutlinedButton
import com.example.shopify.model.product_details.Discount
import com.example.shopify.model.product_details.Price
import com.example.shopify.model.product_details.Product
import com.example.shopify.model.product_details.VariantItem
import com.example.shopify.utils.shopifyLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListProductCardItem(
    product: Product,
    isAddingToCard: Boolean,
    navigateToProductDetails: () -> Unit,
    addToCart: () -> Unit,
    deleteProduct: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = navigateToProductDetails
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .aspectRatio(1f, false)
                    .padding(15.dp),
                contentScale = ContentScale.Inside,
                model = product.images[0],
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
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp),
                text = product.title,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 10.dp),
                verticalAlignment = Alignment.Bottom,

            ) {
                Text(
                    text = product.price.currencyCode,
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = product.price.amount,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 15.dp
                ),
                modifier = Modifier
                    .padding(start = 16.dp, top = 15.dp)
                    .height(20.dp),
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.padding(bottom = 1.dp, start = 5.dp, end = 5.dp),
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic

                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    onClick = { addToCart() },
                    modifier = Modifier
                        .weight(0.9f)
                        .height(28.dp),
                    enabled = product.totalInventory != 0,
                    shape = RoundedCornerShape(7.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_to_cart),
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.shopifyLoading(isAddingToCard,Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                ShopifyOutlinedButton(
                    modifier = Modifier.height(28.dp),
                    onClick = { deleteProduct() },
                    border = BorderStroke(
                        width = .8.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(vertical = 1.4.dp)
                            .size(14.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}


@Preview
@Composable
private fun WishListProductCardItemPreview() {
    WishListProductCardItem(
        product = Product(
            images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
            description = "The Stan Smith owned the tennis court in the '70s." +
                    " Today it runs the streets with the same clean," +
                    " classic style." +
                    " These kids' shoes preserve the iconic look of the original," +
                    " made in leather with punched 3-Stripes," +
                    " heel and tongue logos and lightweight step-in cushioning.",
            totalInventory = 5,
            variants = listOf(VariantItem("", "", "white/1",0, )),
            title = "iPhone 14 Pro 256GB Deep Purple 5G With FaceTime - International Version",
            vendor = "Adidas",
            price = Price(
                amount = "172.00",
                currencyCode = "AED"
            ),
            discount = Discount(
                realPrice = "249.00",
                percent = 30
            )
        ),
        isAddingToCard = false
        , {}, {},{}
    )
}