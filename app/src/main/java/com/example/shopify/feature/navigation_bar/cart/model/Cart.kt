package com.example.shopify.feature.navigation_bar.cart.model

import com.shopify.buy3.Storefront.MoneyV2

data class Cart(
    val lines: List<CartLine> = listOf(),
    val taxes: MoneyV2? = null,
    val subTotalsPrice: MoneyV2? = null,
    val shippingFee: MoneyV2? = null,
    val totalPrice: MoneyV2? = null,
    val checkoutPrice: MoneyV2? = null,
    val coupons: Map<String, MoneyV2> = emptyMap(),
    val discounts: MoneyV2? = null,
    val address:String = "",
    val error: String? = null,
    val couponError: String? = null,
    val hasNextPage: Boolean = false
)
