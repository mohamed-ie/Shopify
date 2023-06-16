package com.example.shopify.feature.navigation_bar.common.model


data class Pageable<D>(
    val data:D,
    val hasNext:Boolean,
    val lastCursor:String
)
