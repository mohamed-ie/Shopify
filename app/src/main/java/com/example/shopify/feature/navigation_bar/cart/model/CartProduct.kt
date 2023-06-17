package com.example.shopify.feature.navigation_bar.cart.model

import com.shopify.graphql.support.ID

data class CartProduct(
    val id: ID = ID(""),
    val name: String,
    val thumbnail: String?,
    val collection: String,
    val vendor: String
)
