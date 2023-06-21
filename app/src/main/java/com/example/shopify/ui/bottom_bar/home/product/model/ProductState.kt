package com.example.shopify.ui.bottom_bar.home.product.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class ProductsState(
    val minPrice: Float = 0.0f,
    val maxPrice: Float = 0f,
    val brandProducts: SnapshotStateList<BrandProduct> = mutableStateListOf(),
    val sliderValue: Float = 0f,
    val isPriceSliderVisible: Boolean = false,
    val isLoggedIn:Boolean = false,
    val brandProductId:String = ""
)