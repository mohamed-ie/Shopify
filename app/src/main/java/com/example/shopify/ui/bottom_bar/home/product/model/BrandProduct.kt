package com.example.shopify.ui.bottom_bar.home.product.model

import com.shopify.buy3.Storefront.MoneyV2
import com.shopify.graphql.support.ID

data class BrandProduct(
    val id: ID,
    val title: String,
    val images: List<String>,
    val price: MoneyV2,
    val isFavourite: Boolean = false
)





