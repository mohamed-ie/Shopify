package com.example.shopify.feature.navigation_bar.home.screen.product.model

import com.shopify.buy3.Storefront.MoneyV2
import com.shopify.graphql.support.ID

data class BrandProduct(
    val id: ID,
    val title: String,
    val images: List<String>,
    val price: MoneyV2,
    val isFavourite: Boolean = false
)





