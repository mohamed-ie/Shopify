package com.example.shopify.feature.navigation_bar.home.screen.product.model

import com.shopify.buy3.Storefront.MoneyV2
import com.shopify.graphql.support.ID

data class BrandProduct(
    val id: ID,
    val title: String, val description: String, val images: List<String>, val brandVariants: BrandVariants
)

data class BrandVariants(val availableForSale: Boolean, val price: MoneyV2)



