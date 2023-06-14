package com.example.shopify.feature.search.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopify.feature.search.viewModel.SearchViewModel
import com.shopify.graphql.support.ID

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navigateToProductDetails:(ID)->Unit,
    back:()->Unit
) {
    val searchedProductsState by viewModel.searchedProductsState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()

    SearchScreenContent(
        searchedProductsState = searchedProductsState,
        navigateToProductDetails = navigateToProductDetails,
        onFavourite = {_,_ ->},
        back = back,
        onValueChange = viewModel::getProductsBySearchKeys,
        searchSectionState = screenState,
        onScrollDown = viewModel::getProductsByLastCursor
    )
}