package com.example.shopify.ui.search.view

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shopify.ui.bottom_bar.home.product.model.BrandProduct


data class SearchedProductsState(
    val productList: SnapshotStateList<BrandProduct> = mutableStateListOf(),
    val searchTextValue:String = "",
    val lastCursor:String = "",
    val hasNext:Boolean = false,
    val isLogged:Boolean = false,
    val isLoadingHasNext:Boolean = false
)
