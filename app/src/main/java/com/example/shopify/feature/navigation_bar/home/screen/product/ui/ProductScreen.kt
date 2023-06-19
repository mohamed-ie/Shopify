package com.example.shopify.feature.navigation_bar.home.screen.product.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.shopify.R
import com.example.shopify.feature.auth.Auth
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.common.SearchHeader
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.home.screen.product.model.ProductsState
import com.example.shopify.feature.navigation_bar.home.screen.product.viewModel.ProductViewModel
import com.example.shopify.feature.navigation_bar.productDetails.ProductDetailsGraph
import com.example.shopify.helpers.firestore.mapper.encodeProductId
import com.shopify.graphql.support.ID

@Composable
fun ProductScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: ProductViewModel,
    back: () -> Unit,
    navigateTo: (String) -> Unit,
    navigateToSearch: () -> Unit
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getProduct()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state by viewModel.productState.collectAsState()
    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> ProductScreenContent(
            productsState = state,
            navigateToHome = back,
            navigateToProductDetails = { navigateTo("${ProductDetailsGraph.PRODUCT_DETAILS}/${it.encodeProductId()}") },
            updateSliderValue = viewModel::updateSliderValue,
            onFavourite = { index ->
                if (state.isLoggedIn)
                    viewModel.onFavourite(index)
                else
                    navigateTo(Auth.SIGN_IN)
            },
            navigateToSearch = navigateToSearch
        )

        ScreenState.ERROR -> ErrorScreen {
            viewModel.getProduct()
        }
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
    onFavourite: (Int) -> Unit,
    navigateToSearch: () -> Unit
) {
    Column() {
        NamedTopAppBar("", navigateToHome)
        SearchHeader { navigateToSearch() }
        Slider(
            minValue = productsState.minPrice,
            maxValue = productsState.maxPrice,
            value = productsState.sliderValue,
            onValueChange = updateSliderValue
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(productsState.brandProducts.count()) { index ->
                productsState.brandProducts[index].run {
                    ProductCard(
                        product = this,
                        onProductItemClick = { navigateToProductDetails(this.id) },
                        onFavouriteClick = { onFavourite(index) }
                    )
                }
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


