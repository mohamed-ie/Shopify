package com.example.shopify.feature.navigation_bar.home.screen.Product.model

data class ProductsState(
    val minPrice: Float = 0.0f,
    val maxPrice: Float = 0f,
    val products: List<Product> = listOf(),
    val sliderValue: Float = 0f
)