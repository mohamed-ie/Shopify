package com.example.shopify.feature.navigation_bar.category.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.category.model.CategoryState
import com.example.shopify.feature.navigation_bar.category.viewModel.CategoryViewModel
import com.example.shopify.feature.navigation_bar.common.ErrorScreen
import com.example.shopify.feature.navigation_bar.common.LoadingScreen
import com.example.shopify.feature.navigation_bar.common.SearchHeader
import com.example.shopify.feature.navigation_bar.common.state.ScreenState
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.theme.shopifyColors
import com.shopify.graphql.support.ID

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    paddingValues: PaddingValues,
    navigateTo: (ID) -> Unit,
    navigateToSearch: () -> Unit

) {
    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> CategoriesScreenContent(
            categoryState = viewModel.categoryState.collectAsState().value,
            paddingValues = paddingValues,
            navigateToProductDetails = navigateTo,
            updateProductTag = viewModel::updateProductTag,
            updateProductType = viewModel::updateProductType,
            navigateToSearch = navigateToSearch
        )

        ScreenState.ERROR -> ErrorScreen { viewModel.loadData() }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreenContent(
    categoryState: CategoryState,
    paddingValues: PaddingValues,
    navigateToProductDetails: (ID) -> Unit,
    updateProductTag: (Int) -> Unit,
    updateProductType: (Int) -> Unit,
    navigateToSearch: () -> Unit
) {
    Scaffold(topBar = {
        Column {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
            SearchHeader(onClick = navigateToSearch)
        }
    }) {
        Column(modifier = Modifier.padding(it)) {

            TabRow(
                selectedTabIndex = categoryState.selectedProductTypeIndex,
                modifier = Modifier.fillMaxWidth()

            ) {
                categoryState.productType.forEachIndexed { index, item ->
                    Tab(
                        selected = categoryState.selectedProductTypeIndex == index,
                        onClick = {
                            updateProductType(index)
                        }
                    ) {
                        Text(
                            text = item, modifier = Modifier.padding(vertical = 16.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier
                        .background(MaterialTheme.shopifyColors.Silver)
                        .weight(3f)
                ) {
                    itemsIndexed(categoryState.productTag) { index, item ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    updateProductTag(index)
                                }
                                .weight(1f)
                                .fillMaxWidth()
                                .background(
                                    color = if (categoryState.selectedProductTagIndex == index)
                                        Color.White
                                    else
                                        Color.Transparent,
                                )
                                .padding(vertical = 16.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .weight(10f)
                ) {
                    items(categoryState.productsList) { product ->
                        CategoryProductCard(product = product) {
                            navigateToProductDetails(product.id)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategoryScreen() {
    ShopifyTheme() {
        CategoriesScreenContent(
            categoryState = CategoryState(),
            paddingValues = PaddingValues(),
            navigateToProductDetails = {},
            updateProductTag = {},
            updateProductType = {},
            navigateToSearch = {}
        )
    }
}



