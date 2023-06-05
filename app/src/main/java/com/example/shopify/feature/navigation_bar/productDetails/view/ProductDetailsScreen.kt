package com.example.shopify.feature.navigation_bar.productDetails.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.common.ErrorScreen
import com.example.shopify.feature.common.LoadingContent
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.productDetails.viewModel.ProductDetailsViewModel


@Composable
fun ProductDetailsScreen(
    id:String,
    viewModel: ProductDetailsViewModel
) {
    val product by viewModel.productState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val addToCardState by viewModel.addToCardState.collectAsState()
    val variantsState by viewModel.variantState.collectAsState()

    viewModel.getProduct(id)
    when(screenState){
        ScreenState.LOADING -> LoadingContent()
        ScreenState.STABLE -> {
            ProductDetailsScreenContent(
                product = product,
                addToCardState = addToCardState,
                variantsState = variantsState,
                back = {},
                viewCart = {}
            )
        }
        ScreenState.ERROR -> ErrorScreen {}
    }

}