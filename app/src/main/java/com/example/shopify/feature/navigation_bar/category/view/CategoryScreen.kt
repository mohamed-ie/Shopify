package com.example.shopify.feature.navigation_bar.category.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.common.LoadingScreen
import com.example.shopify.feature.common.state.ScreenState
import com.example.shopify.feature.navigation_bar.category.model.CategoryState
import com.example.shopify.feature.navigation_bar.category.viewModel.CategoryViewModel
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.ui.screen.common.SearchBarItem
import com.shopify.graphql.support.ID

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    paddingValues: PaddingValues,
    navigateTo: (ID) -> Unit
) {
    when (viewModel.screenState.collectAsState().value) {
        ScreenState.LOADING -> LoadingScreen()
        ScreenState.STABLE -> CategoriesScreenContent(
            categoryState = viewModel.categoryState.collectAsState().value,
            paddingValues = paddingValues,
            navigateToProductDetails = navigateTo,
            updateProductTag = viewModel::updateProductTag,
            updateProductType = viewModel::updateProductType
        )

        ScreenState.ERROR -> {}
    }

}

@Composable
fun CategoriesScreenContent(
    categoryState: CategoryState,
    paddingValues: PaddingValues,
    navigateToProductDetails: (ID) -> Unit,
    updateProductTag: (Int) -> Unit,
    updateProductType: (Int) -> Unit,

    ) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.shopfiy_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 20.dp)
            )
            SearchBarItem(onSearch = {})
        }
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
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.LightGray)
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
                        Text(text = item)
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(10f)
                    .padding(16.dp)
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

@Preview
@Composable
fun previewCategoryScreen() {
    ShopifyTheme() {
        CategoriesScreenContent(
            categoryState = CategoryState(),
            paddingValues = PaddingValues(),
            navigateToProductDetails = {},
            updateProductTag = {},
            updateProductType = {}
        )
    }
}



