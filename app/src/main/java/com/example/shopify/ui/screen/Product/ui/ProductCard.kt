package com.example.shopify.ui.screen.Product.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.ui.screen.Product.model.Product

@Composable
fun RowScope.ProductCard(product: Product) {
    Box(modifier = Modifier.weight(1f)) {
        Card(
            modifier = Modifier
                .padding(10.dp),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            )
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                ImageCardScrollHorizontally(product.images)
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Text(
                    text = product.title, fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,

                    )
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
                Row() {
                    Spacer(modifier = Modifier.padding(bottom = 10.dp))
                    Text(
                        text = buildAnnotatedString {
                            append(product.variants.price.currencyCode.toString())
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp
                                )
                            )
                            { append(product.variants.price.amount.toString()) }
                        },
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
        FloatFavouriteButton()
    }

}
