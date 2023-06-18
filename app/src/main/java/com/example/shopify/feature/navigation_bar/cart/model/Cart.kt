package com.example.shopify.feature.navigation_bar.cart.model

import com.example.shopify.feature.address.addresses.model.MyAccountMinAddress
import com.shopify.buy3.Storefront.MoneyV2

data class Cart(
    val lines: List<CartLine> = listOf(),
    val taxes: String ="" ,
    val subTotalsPrice: String = "",
    val shippingFee: String = "",
    val totalPrice: String="",
    val checkoutPrice: String? = null,
    val coupons: Map<String, MoneyV2> = emptyMap(),
    val discounts: String? = null,
    val address: MyAccountMinAddress = MyAccountMinAddress(),
    val error: String? = null,
    val couponError: String? = null,
    val hasNextPage: Boolean = false,
    val endCursor: String = "",
    val isLoggedIn:Boolean = false
)
