package com.example.shopify.ui.screen.Product.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.ui.screen.Product.viewModel.ProductViewModel
import com.example.shopify.ui.screen.common.LoadingContent
import com.example.shopify.ui.screen.common.NamedTopAppBar
import com.example.shopify.ui.screen.common.SearchBarItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel,
    paddingValues: PaddingValues,
    brandName: String,
    navigateToHome: () -> Unit
) {
    viewModel.getProduct(brandName)
    val productList by viewModel.productList.collectAsState(initial = listOf())
    val sliderValue = remember { mutableStateOf(20f) }
    val filterProductList = productList.filter {
        it.variants.price.amount.toDouble() > sliderValue.value
    }
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        stickyHeader {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(vertical = 8.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
            ) {
                NamedTopAppBar("", navigateToHome)
                SearchBarItem(onSearch = {})
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    androidx.compose.material3.Slider(
                        value = sliderValue.value,
                        onValueChange = { sliderValue.value = it },
                        valueRange = 10f..1000f,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${(sliderValue.value).toInt()}",
                        modifier = Modifier.padding(start = 10.dp, top = 6.dp),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        if (filterProductList?.isNotEmpty()!!) {
            for (i in 0 until (filterProductList?.size ?: 0) step (2))
                item {
                    Row() {
                        ProductCard(product = filterProductList[i])
                        filterProductList!!.getOrNull(i + 1)?.let {
                            ProductCard(product = it)
                        }
                    }
                }
        } else {
            item {
                LoadingContent()
            }

        }
    }
}

