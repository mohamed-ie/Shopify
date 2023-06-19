package com.example.shopify.feature.search.view

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct


data class SearchedProductsState(
    val productList: SnapshotStateList<BrandProduct> = mutableStateListOf(),
    val searchTextValue:String = "",
    val lastCursor:String = "",
    val hasNext:Boolean = false,
    val isLogged:Boolean = false,
    val isLoadingHasNext:Boolean = false
)
