package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.common.component.RatingBar
import com.example.shopify.feature.common.component.ShopifyOutlinedButton
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ReviewsState
import com.example.shopify.theme.shopifyColors


@Composable
fun ReviewsSection(
    reviewsState: ReviewsState,
    lastSection:@Composable () -> Unit = {},
    divider:@Composable () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)
                ){
            Text(
                text = stringResource(R.string.user_reviews),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = reviewsState.averageRating.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    RatingBar(
                        rating = reviewsState.averageRating ,
                        filedStarsColor = MaterialTheme.shopifyColors.Orange
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.based_on_ratings,reviewsState.ratingCount),
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider(color =  MaterialTheme.shopifyColors.ServerColor, thickness = 1.dp)
        Text(
            text = stringResource(R.string.there_are_customer_ratings_and_customer_reviews,reviewsState.ratingCount,reviewsState.reviewCount),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
        )
        divider()
        Spacer(modifier = Modifier.height(7.dp))
        Column{
            repeat(times = reviewsState.reviews.count()){reviewsContentIndex ->
                ReviewItemCard(review =  reviewsState.reviews[reviewsContentIndex])
            }
        }
        lastSection()
    }
}


@Preview
@Composable
fun ReviewsSectionPreview() {
    ReviewsSection(
        reviewsState = ReviewsState(averageRating = 3.5,
            reviewCount = 66,
            ratingCount = 20,
            reviews = listOf(
                Review(
                    reviewer = "",
                    rate = 2.5,
                    review = "Perfect phone",
                    description = "Its very nice experience Ga iloved it",
                    time = "2 months ago"
                )
            )),
        {})
    //ReviewItemCard("Rees R.",2.5,"Perfect phone","Its very nice experience Ga iloved it","2 months ago")
}

