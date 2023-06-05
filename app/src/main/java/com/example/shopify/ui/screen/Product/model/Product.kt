package com.example.shopify.ui.screen.Product.model

import com.shopify.buy3.Storefront.MoneyV2
import com.shopify.graphql.support.ID

data class Product(
    val id: ID,
    val title: String, val description: String, val images: List<String>, val variants: Variants
)

data class Variants(val availableForSale: Boolean, val price: MoneyV2)



