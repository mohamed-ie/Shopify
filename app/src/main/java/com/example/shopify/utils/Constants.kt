package com.example.shopify.utils

import androidx.datastore.preferences.core.stringPreferencesKey
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

    object DataStoreKeys{
        const val USER = "USER_PREFERENCES"
        val USER_EMAIL = stringPreferencesKey("USER_EMAIL_PREFERENCES")
        val USER_PASSWORD = stringPreferencesKey("USER_PASSWORD_PREFERENCES")
        val USER_ACCESS_TOKEN= stringPreferencesKey("USER_ACCESS_TOKEN_PREFERENCES")
        val USER_EXPIRED_TOKEN_AT = stringPreferencesKey("USER_EXPIRED_TOKEN_AT_PREFERENCES")
    }

}
