package com.example.shopify.model.cart.order

import com.shopify.buy3.Storefront.MoneyV2
import com.shopify.graphql.support.ID

data class LineItems(
    val id: ID = ID(""),
    val name: String = "",
    val thumbnail: String = "",
    val collection: String = "",
    val vendor: String = "",
    val description: String = "",
    val price: MoneyV2 = MoneyV2()
)