package com.example.shopify.model.cart.order

import com.shopify.buy3.Storefront

fun Storefront.MailingAddress.getFullAddress(): String {
    val addressComponents = mutableListOf<String>()

    address1?.let { addressComponents.add(it) }
    address2?.let { addressComponents.add(it) }
    city?.let { addressComponents.add(it) }
    country?.let { addressComponents.add(it) }
    province?.let { addressComponents.add(it) }

    return addressComponents.joinToString(", ")
}