package com.example.shopify.ui.screen.productDetails.view

import com.example.shopify.ui.screen.productDetails.model.VariantItem

data class VariantsState(
    val variants:List<VariantItem> = listOf(),
    val selectedVariant:Int = 1,
    val selectVariant:(Int)->Unit
)