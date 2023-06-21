package com.example.shopify.model


data class Pageable<D>(
    val data:D,
    val hasNext:Boolean,
    val lastCursor:String
)
