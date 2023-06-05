package com.example.shopify.ui.screen.productDetails.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.ui.screen.common.ErrorScreen
import com.example.shopify.ui.screen.common.LoadingContent
import com.example.shopify.ui.screen.common.model.ScreenState
import com.example.shopify.ui.screen.productDetails.viewModel.ProductDetailsViewModel


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