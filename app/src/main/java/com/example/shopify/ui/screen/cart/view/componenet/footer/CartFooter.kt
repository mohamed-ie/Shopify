package com.example.shopify.ui.screen.cart.view.componenet.footer

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.screen.cart.view.cartElevation
import com.example.shopify.ui.theme.Gray
import com.example.shopify.ui.theme.ShopifyTheme

@Composable
fun CartFooter(itemsCount: Int, totalPrice: String, checkout: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.padding(top = 2.dp),
        elevation = CardDefaults.elevatedCardElevation(cartElevation),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp, top = 8.dp)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (itemsCount == 1)
                        stringResource(id = R.string.one_item)
                    else
                        stringResource(id = R.string.number_of_items, itemsCount),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Gray
                )
                Text(
                    text = totalPrice,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = checkout,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = stringResource(id = R.string.checkout).uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                }
                AnimatedMovingForwardIcon()
            }
        }
    }
}

@Composable
private fun BoxScope.AnimatedMovingForwardIcon() {

    val animatedTransition = rememberInfiniteTransition()

    val paddingEnd by animatedTransition.animateValue(
        initialValue = 16.dp,
        targetValue = 16.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2500
                16.dp at 0
                8.dp at 500
                16.dp at 1000
                8.dp at 1500
                16.dp at 2000
                16.dp at 2500
            },
            repeatMode = RepeatMode.Restart
        )
    )

    Icon(
        modifier = Modifier
            .padding(end = paddingEnd)
            .clip(shape = MaterialTheme.shapes.extraSmall)
            .background(Color.White)
            .align(Alignment.CenterEnd),
        imageVector = Icons.Rounded.ArrowForward,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = null
    )
}

@Preview
@Composable
fun PreviewCartFooterNumberOfItems() {
    ShopifyTheme {
        CartFooter(itemsCount = 5, totalPrice = "EGP 167.50", {})
    }
}

@Preview
@Composable
fun PreviewCartFooter1Item() {
    ShopifyTheme {
        CartFooter(itemsCount = 1, totalPrice = "EGP 12.00", {})
    }
}