package com.example.shopify.feature.common.model

data class LoadableContent<D>(
    val data: D,
    val isLoading: Boolean = false
)
