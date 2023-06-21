package com.example.shopify.model.cart.cart

import com.example.shopify.model.address.MyAccountMinAddress

data class Cart(
    val lines: List<CartLine> = listOf(),
    val taxes: String ="",
    val subTotalsPrice: String ="",
    val shippingFee: String ="",
    val currencyCode: String = "EGP",
    val totalPrice: String = "",
    val checkoutPrice: String? = null,
    val discounts: String? = null,
    val address: MyAccountMinAddress = MyAccountMinAddress(),
    val error: String? = null,
    val couponError: String? = null,
    val hasNextPage: Boolean = false,
    val endCursor: String = "",
)
