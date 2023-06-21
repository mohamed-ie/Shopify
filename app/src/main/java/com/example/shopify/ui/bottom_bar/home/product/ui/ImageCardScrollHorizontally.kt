@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.shopify.ui.bottom_bar.home.product.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.ui.theme.ShopifyTheme
import com.example.shopify.utils.shopifyLoading

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCardScrollHorizontally(
    isAvailable:Boolean = true,
    images: List<String>,
    isFavourite: Boolean,
    addToFavourite: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        images.size
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState, modifier = Modifier.fillMaxWidth()
            ) { page ->
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .aspectRatio(1f, false)
                        .padding(15.dp),
                    contentScale = ContentScale.Inside,
                    model = images[page],
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
            }
            if (isAvailable)
                IconButton(
                    onClick = addToFavourite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 12.dp)
                        .shadow(1.dp, shape = CircleShape, spotColor = Color.Black)
                        .clip(CircleShape)
                        .size(35.dp)
                        .background(Color.White)

                ) {
                    if (isFavourite) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                    }

                }
        }
        DotIndicator(
            pageCount = images.size, currentPage = pagerState.currentPage
        )
    }

}

@Composable
fun DotIndicator(pageCount: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(5.dp)
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
            .padding(8.dp)
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
                Icons.Rounded.FavoriteBorder, contentDescription = "Favorite", tint = Color.Gray
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImageCardScrollHorizontally() {
    ShopifyTheme {
        //   ImageCardScrollHorizontally(listOf())
    }
}