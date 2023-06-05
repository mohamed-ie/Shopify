package com.example.shopify.feature.navigation_bar.home.screen.Product.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.shopify.feature.common.NamedTopAppBar
import com.example.shopify.ui.screen.Product.ui.Slider
import com.example.shopify.ui.screen.Product.viewModel.ProductViewModel
import com.example.shopify.ui.screen.common.SearchBarItem

@Composable
fun ProductScreen(
    viewModel: ProductViewModel,
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit
) {
    val productsState by viewModel.productList.collectAsState()
    Column(modifier = Modifier.padding(paddingValues)) {
        NamedTopAppBar("", navigateToHome)
        SearchBarItem(onSearch = {})
        Slider(
            minValue = productsState.minPrice,
            maxValue = productsState.maxPrice,
            value = productsState.sliderValue,
            onValueChange = { viewModel.updateSliderValue(it) }
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
            items(productsState.products) {
                ProductCard(it)
            }
        }
    }
}

