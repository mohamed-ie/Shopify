package com.example.shopify.feature.navigation_bar.home.screen.product.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.shopify.graphql.support.ID

data class ProductsState(
    val minPrice: Float = 0.0f,
    val maxPrice: Float = 0f,
    val brandProducts: SnapshotStateList<BrandProduct> = mutableStateListOf(),
    val sliderValue: Float = 0f,
    val isPriceSliderVisible: Boolean = false,
    val isLoggedIn:Boolean = false,
    val brandProductId:String = ""
)