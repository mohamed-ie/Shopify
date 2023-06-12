package com.example.shopify.feature.address.addresses.model

import com.shopify.graphql.support.ID

class MyAccountMinAddress(
    val id: ID,
    val name: String,
    val address: String,
    val phone: String,
    val isDefault: Boolean =false
)