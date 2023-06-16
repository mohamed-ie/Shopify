package com.example.shopify.feature.navigation_bar.home.screen.product.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.common.SearchHeader
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.home.screen.product.model.ProductsState
import com.example.shopify.feature.navigation_bar.home.screen.product.viewModel.ProductViewModel
import com.example.shopify.feature.navigation_bar.productDetails.ProductDetailsGraph
import com.example.shopify.helpers.firestore.mapper.encodeProductId
import com.example.shopify.ui.screen.Product.ui.Slider
import com.shopify.graphql.support.ID

@Composable
fun ProductScreen(
    viewModel: ProductViewModel,
    back: () -> Unit,
    navigateTo: (String) -> Unit,
    navigateToSearch: () -> Unit
) {
    val state by viewModel.productList.collectAsState()
    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> ProductScreenContent(
            productsState = state,
            navigateToHome = back,
            navigateToProductDetails = { navigateTo("${ProductDetailsGraph.PRODUCT_DETAILS}/${it.encodeProductId()}") },
            updateSliderValue = viewModel::updateSliderValue,
            onFavourite = viewModel::onFavourite,
            navigateToSearch = navigateToSearch

        )

        ScreenState.ERROR -> {}
    }
    PriceSliderDialog(
        productsState = state,
        updateSliderValue = viewModel::updateSliderValue,
        hideDialog = viewModel::hideDialog,
        apply = viewModel::apply
    )
}

@Composable
fun ProductScreenContent(
    productsState: ProductsState,
    navigateToHome: () -> Unit,
    navigateToProductDetails: (ID) -> Unit,
    updateSliderValue: (Float) -> Unit,
    onFavourite: (ID, Boolean) -> Unit,
    navigateToSearch: () -> Unit
) {
    Column() {
        NamedTopAppBar("", navigateToHome)
        SearchHeader(onClick = navigateToSearch)
        Slider(
            minValue = productsState.minPrice,
            maxValue = productsState.maxPrice,
            value = productsState.sliderValue,
            onValueChange = updateSliderValue
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
            items(productsState.brandProducts) {
                ProductCard(
                    product = it,
                    onProductItemClick = { navigateToProductDetails(it.id) },
                    onFavouriteClick = { onFavourite(it.id, it.isFavourite) }
                )
            }
        }
    }
}

@Composable
fun PriceSliderDialog(
    productsState: ProductsState,
    updateSliderValue: (Float) -> Unit,
    hideDialog: () -> Unit,
    apply: () -> Unit
) {
    AnimatedVisibility(visible = productsState.isPriceSliderVisible) {
        Dialog(onDismissRequest = hideDialog) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.price_range),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${productsState.minPrice} - ${productsState.maxPrice}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        minValue = productsState.minPrice,
                        maxValue = productsState.maxPrice,
                        value = productsState.sliderValue,
                        onValueChange = updateSliderValue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = apply) {
                        Text(text = stringResource(id = R.string.apply))
                    }
                }
            }
        }
    }
}


