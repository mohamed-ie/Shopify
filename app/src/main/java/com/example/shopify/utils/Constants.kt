package com.example.shopify.utils

import com.example.shopify.BuildConfig

class Constants private constructor() {
    object SHOPIFY {
        const val BASE_URL = "https://${BuildConfig.DOMAIN}/admin/api/2023-04/graphql.json"
    }

    object RegexPatterns {
        const val EMAIL_PATTERN = "[a-zA-Z]\\S*@[a-zA-Z]\\S*\\.[a-zA-Z].*"
        const val PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}\$"
        const val PHONE_PATTERN = "^\\+\\d{1,3}\\d{10}\$"
    }

}
