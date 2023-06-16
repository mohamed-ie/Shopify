package com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order

import com.shopify.graphql.support.ID

data class LineItems(
    val id: ID = ID(""),
    val name: String = "",
    val thumbnail: String = "",
    val collection: String = "",
    val vendor: String = "",
    val description: String = ""
)