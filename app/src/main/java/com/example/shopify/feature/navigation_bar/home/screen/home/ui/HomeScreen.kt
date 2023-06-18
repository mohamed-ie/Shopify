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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.SearchHeader
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.home.screen.HomeGraph
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.home.viewModel.BrandViewModel
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.utils.shopifyLoading
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: BrandViewModel,
    navigateTo: (String) -> Unit,
    navigateToSearch: () -> Unit

) {
    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> HomeScreenContent(
            brandList = viewModel.brandList.collectAsState(initial = emptyList()).value,
            navigateToProduct = { navigateTo("${HomeGraph.PRODUCTS}/$it") },
            navigateToSearch = navigateToSearch
        )

        ScreenState.ERROR -> ErrorScreen { viewModel.getBrandList() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandListItem(item: Brand, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(0.75f),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { onItemClick(item.title) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .weight(1f),
                model = item.url,
                contentScale = ContentScale.Fit,
                contentDescription = null,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
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
                })
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

@Composable
fun HomeScreenContent(
    brandList: List<Brand>,
    navigateToProduct: (String) -> Unit,
    navigateToSearch: () -> Unit
) {
    Scaffold(topBar = {
        SearchHeader(onClick = navigateToSearch)
    }, modifier = Modifier.padding(top = 16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // SalesCard()

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(4.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "Brands",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(15.dp),
                        fontSize = 25.sp
                    )
                }
                items(brandList) {
                    BrandListItem(item = it) { brandName ->
                        navigateToProduct(brandName)
                    }
                }
            }

        }

    }

}

@Preview
@Composable
fun PreviewHomeScreen() {
    ShopifyTheme {
        //HomeScreen(viewModel = hiltViewModel(), PaddingValues(), navigateToProduct = {})
    }
}


