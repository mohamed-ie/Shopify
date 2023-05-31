package com.example.shopify.utils

import com.example.shopify.BuildConfig

class Constants private constructor() {
    object SHOPIFY {
        const val BASE_URL = "https://${BuildConfig.DOMAIN}/admin/api/2023-04/graphql.json"
    }

}
