package com.example.shopify.feature.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.home.screen.product.ui.ProductCard
import com.example.shopify.feature.search.components.SearchTopBar
import com.example.shopify.theme.shopifyColors
import com.example.shopify.utils.shopifyLoading
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

@Composable
fun SearchScreenContent(
    searchedProductsState:SearchedProductsState,
    searchSectionState: ScreenState,
    navigateToProductDetails: (ID) -> Unit,
    onFavourite: (Int) -> Unit,
    back:()->Unit,
    onValueChange:(String) -> Unit,
    onScrollDown:() -> Unit
) {
    Column {
        SearchTopBar(
            searchedText = searchedProductsState.searchTextValue,
            back = back,
            onValueChange = onValueChange
        )
        Row(
            modifier = Modifier
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(R.string.results),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = searchedProductsState.productList.count().toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.shopifyLoading(searchedProductsState.isLoadingHasNext)
            )
        }
        val lazyListState = rememberLazyGridState()
        if (lazyListState.isScrollInProgress)
            DisposableEffect(key1 = Unit) {
                onDispose {
                    onScrollDown()
                }
            }
        when(searchSectionState){
            ScreenState.LOADING -> LoadingScreen()
            ScreenState.STABLE -> {
                LazyVerticalGrid(
                    state = lazyListState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .background(MaterialTheme.shopifyColors.ServerColor)
                        .weight(1f)
                        .padding(vertical = 5.dp)
                ) {
                    items(searchedProductsState.productList.count()) {productIndex ->
                        searchedProductsState.productList[productIndex].run {
                            ProductCard(
                                product = this,
                                onProductItemClick = { navigateToProductDetails(this.id) },
                                onFavouriteClick = { onFavourite(productIndex) }
                            )
                        }
                    }
                }
            }
            ScreenState.ERROR -> ErrorScreen{}
        }
    }
}



@Preview
@Composable
private fun SearchScreenContentPreview() {
    SearchScreenContent(
        searchedProductsState = SearchedProductsState(
            productList = listOf(
                BrandProduct(
                    id = ID(""),
                    title = "Ultima show Running Shoes Pink",
                    images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
                    isFavourite = false,
                    price = Storefront.MoneyV2().setAmount("200").setCurrencyCode(Storefront.CurrencyCode.SDG)
                )
            ) as SnapshotStateList<BrandProduct>
        ),
        searchSectionState = ScreenState.LOADING,
        navigateToProductDetails = {},
        onFavourite = {},
        back = {},
        onValueChange = {},
        onScrollDown = {}
    )
}