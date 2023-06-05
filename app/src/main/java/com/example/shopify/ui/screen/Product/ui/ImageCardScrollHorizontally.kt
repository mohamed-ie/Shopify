@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.shopify.ui.screen.Product.ui

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.helpers.ImageFromUrl
import com.example.shopify.ui.theme.ShopifyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCardScrollHorizontally(images: List<String>) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        images.size
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) { page ->
                Card(
                    modifier = Modifier
                        .padding(1.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(containerColor = Color.White)

                ) {
                    ImageFromUrl(url = images[page])
                }

            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        DotIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
fun DotIndicator(pageCount: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(if (index == currentPage) Color.Black else Color.Gray)
            )
        }
    }
}

@Composable
fun BoxScope.FloatFavouriteButton() {

    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(Color.White)
            .align(Alignment.TopEnd)
            .shadow(1.dp, shape = CircleShape)
    ) {

        IconButton(
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(10.dp)
                .size(40.dp),

            ) {
            Icon(
                Icons.Rounded.FavoriteBorder,
                contentDescription = "Favorite", tint = Color.Gray
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImageCardScrollHorizontally() {
    ShopifyTheme {
        ImageCardScrollHorizontally(listOf())
    }
}