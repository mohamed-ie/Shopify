package com.example.shopify.feature.navigation_bar.home.screen.product.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct

@Composable
fun ProductCard(brandProduct: BrandProduct) {
    Box() {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .height(300.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ImageCardScrollHorizontally(brandProduct.images)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    text = brandProduct.title,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium

                )
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    text = buildAnnotatedString {
                        append(brandProduct.brandVariants.price.currencyCode.toString())
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        { append(brandProduct.brandVariants.price.amount.toString()) }
                    },
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.padding(bottom = 15.dp))
            }
        }
        FloatFavouriteButton()
    }

}
