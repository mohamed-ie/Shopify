package com.example.shopify.ui.product_details.product_details.view

import com.example.shopify.model.product_details.VariantItem


data class VariantsState(
    val variants:List<VariantItem> = listOf(),
    val selectedVariant:Int = 1,
    val isLowStock:Boolean = false,
    val selectVariant:(Int)->Unit,
    val isAvailable:Boolean = false
)