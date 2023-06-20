package com.example.shopify.feature.navigation_bar.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.shopify.theme.shopifyColors


@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    filedStarsColor: Color = Color.Yellow,
    nonFiledStarsColor: Color = MaterialTheme.shopifyColors.ServerColor,
    selectedReview:((Int)->Unit)? = null
) {
    val filledStars = kotlin.math.floor(rating).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {index ->
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = filedStarsColor,
                modifier = Modifier
                    .clickable { selectedReview?.let { it(index) } }
            )
        }
        if (halfStar) {
            Box {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = nonFiledStarsColor,
                )
                Icon(
                    imageVector = Icons.Rounded.StarHalf,
                    contentDescription = null,
                    tint = filedStarsColor,
                )

            }
        }
        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = nonFiledStarsColor,
                modifier = Modifier
                    .clickable { selectedReview?.let { index -> index(it + filledStars+1) } }
            )
        }
    }
}