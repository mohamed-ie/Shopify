package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model

import com.shopify.graphql.support.ID


data class Product(
    val image: String = "",
    val description: String = "",
    val totalInventory: Int = 0,
    val variants: List<VariantItem> = listOf(),
    val title: String = "",
    val price: Price = Price("", ""),
    val discount: Discount = Discount("", 0),
    val vendor: String = "",
    val id: ID = ID(""),
)


data class Price(
    val amount: String,
    val currencyCode: String
)

data class Discount(
    val realPrice: String,
    val percent: Int
)

data class VariantItem(
    val image: String?,
    val price: String?,
    val title: String?,
    val id: ID? = null
)





