package com.example.shopify.feature.navigation_bar.home.screen.home.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopify.feature.navigation_bar.home.screen.home.ImageFromUrl
import com.example.shopify.feature.common.LoadingContent
import com.example.shopify.feature.common.SearchBarItem
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.home.viewModel.BrandViewModel
import com.example.shopify.theme.ShopifyTheme
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: BrandViewModel, paddingValues: PaddingValues) {
    viewModel.getBrandList()
    val brandList by viewModel.brandList.collectAsState(initial = listOf())
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        stickyHeader {
            SearchBarItem(onSearch = {})
        }
        item {
            SalesCard()
        }
        item {
            Text(
                text = "Brands",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(15.dp),
                fontSize = 25.sp
            )
        }
        if (brandList?.isNotEmpty()!!) {
            for (i in 0..(brandList?.size ?: 0) step (2))
                item {
                    Row() {
                        BrandListItem(item = brandList!![i])
                        brandList!!.getOrNull(i + 1)?.let {
                            BrandListItem(item = it)
                        }
                    }
                }
        } else {
            item {
                LoadingContent()
            }

        }
    }
}

@Composable
fun RowScope.BrandListItem(item: Brand) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .weight(1f),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ImageFromUrl(url = item.url)
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            Text(
                item.title, fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SalesCard() {
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(20.dp)
            .size(width = 400.dp, height = 150.dp)
    ) {

        var shakeState by remember { mutableStateOf(false) }
        val infiniteTransition = rememberInfiniteTransition()

        LaunchedEffect(Unit) {
            while (true) {
                shakeState = true
                delay(100)
                shakeState = false
                delay(100)
            }
        }

        val offsetX by animateFloatAsState(
            targetValue = if (shakeState) 20f else 0f,
            animationSpec = repeatable(
                iterations = AnimationConstants.DefaultDurationMillis,
                animation = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
        )
        val color by infiniteTransition.animateColor(
            initialValue = Color.Red,
            targetValue = Color.Green,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 2000
                    Color.Red at 0 with LinearOutSlowInEasing
                    Color.Green at 500 with FastOutLinearInEasing
                    Color.Blue at 1000
                },
                repeatMode = RepeatMode.Reverse
            )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "30% off",
                fontSize = 50.sp,
                color = color,
                modifier = Modifier.offset(x = offsetX.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }

}


@Preview
@Composable
fun PreviewHomeScreen() {
    ShopifyTheme {
        HomeScreen(viewModel = hiltViewModel(), PaddingValues())
    }
}


