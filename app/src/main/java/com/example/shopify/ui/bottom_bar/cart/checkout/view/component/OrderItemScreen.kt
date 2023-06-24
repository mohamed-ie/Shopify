package com.example.shopify.ui.bottom_bar.cart.checkout.view.component

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
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.R
import com.example.shopify.model.cart.cart.CartLine
import com.example.shopify.ui.theme.Gray
import com.example.shopify.utils.shopifyLoading

@Composable
fun OrderItemScreen(
    cartLine: CartLine,
) {
    val product = cartLine.cartProduct
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .aspectRatio(1f, true)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                model = product.thumbnail,
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
                    text = product.collection,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray
                )
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${cartLine.currencyCode} ${cartLine.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.sold_by))
                        append(" ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(product.vendor)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}