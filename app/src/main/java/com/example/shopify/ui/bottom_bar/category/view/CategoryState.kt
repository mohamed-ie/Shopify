package com.example.shopify.ui.bottom_bar.category.view

import com.example.shopify.ui.bottom_bar.home.product.model.BrandProduct

data class CategoryState(
    val productType: List<String> = listOf(),
    val productTag: List<String> = listOf(),
    val productsList: List<BrandProduct> = listOf(),
    var selectedProductTypeIndex: Int = 0,
    var selectedProductTagIndex: Int = 0
)
