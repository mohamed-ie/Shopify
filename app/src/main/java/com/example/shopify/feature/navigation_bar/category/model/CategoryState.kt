package com.example.shopify.feature.navigation_bar.category.model

import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct

data class CategoryState(
    val productType: List<String> = listOf(),
    val productTag: List<String> = listOf(),
    val productsList: List<BrandProduct> = listOf(),
    var selectedProductTypeIndex: Int = 0,
    var selectedProductTagIndex: Int = 0
)
