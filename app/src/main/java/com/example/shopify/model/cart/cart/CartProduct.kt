package com.example.shopify.model.cart.cart

import com.shopify.graphql.support.ID

data class CartProduct(
    val id: ID = ID(""),
    val name: String,
    val thumbnail: String?,
    val collection: String,
    val vendor: String
)
