package com.example.shopify.feature.navigation_bar.common.model

data class LoadableContent<D>(
    val data: D,
    val isLoading: Boolean = false
)
