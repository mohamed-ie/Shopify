package com.example.shopify.feature.navigation_bar.home.screen.product.model

data class ProductsState(
    val minPrice: Float = 0.0f,
    val maxPrice: Float = 0f,
    val brandProducts: List<BrandProduct> = listOf(),
    val sliderValue: Float = 0f
)