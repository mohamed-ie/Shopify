package com.example.shopify.feature.navigation_bar.cart.view.componenet.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.common.component.ShopifyOutlinedButton
import com.example.shopify.feature.navigation_bar.cart.view.cartElevation
import com.example.shopify.theme.Gray
import com.example.shopify.theme.ShopifyTheme

@Composable
fun CartHeader(
    itemsCount: Int,
    address: String,
    navigateToWishlistScreen: () -> Unit,
    navigateToMapScreen: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(bottom = 2.dp),
        elevation = CardDefaults.elevatedCardElevation(cartElevation),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.cart))
                        if (itemsCount > 0) {
                            withStyle(style = SpanStyle(color = Gray,)) {
                                append(" (")
                                append(
                                    if (itemsCount == 1)
                                        stringResource(id = R.string.one_item)
                                    else
                                        stringResource(id = R.string.number_of_items, itemsCount)
                                )
                                append(")")
                            }
                        }
                    },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                ShopifyOutlinedButton(onClick = navigateToWishlistScreen) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Rounded.FavoriteBorder,
                        tint = Gray,
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

                    Text(
                        text = stringResource(id = R.string.wishlist),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(color = Color.LightGray.copy(alpha = .5f))
                    .clickable(onClick = navigateToMapScreen)
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Thin)) {
                            append(stringResource(id = R.string.deliver_to))
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" ")
                            append(address)
                        }
                    },
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCartHeaderNumberOfItems() {
    ShopifyTheme {
        CartHeader(itemsCount = 5, address = "EGP 167.50", {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCartHeader1Item() {
    ShopifyTheme {
        CartHeader(itemsCount = 1, address = "EGP 12.00", {}, {})
    }
}