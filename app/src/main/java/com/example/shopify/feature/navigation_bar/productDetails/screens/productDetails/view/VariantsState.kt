package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.VariantItem


data class VariantsState(
    val variants:List<VariantItem> = listOf(),
    val selectedVariant:Int = 1,
    val isLowStock:Boolean = false,
    val selectVariant:(Int)->Unit,
    val isAvailable:Boolean = false
)