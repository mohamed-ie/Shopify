package com.example.shopify.utils

import androidx.datastore.preferences.core.stringPreferencesKey

class Constants private constructor() {
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
        val CUSTOMER_ID = stringPreferencesKey("CUSTOMER_ID")
        val CURRENCY = stringPreferencesKey("CURRENCY")
        val CURRENCY_AMOUNT = stringPreferencesKey("CURRENCY_AMOUNT")
    }

    object ApiLayerCurrency{
        const val BASE_URL = "https://api.apilayer.com/currency_data/"
        const val API_KEY = "0uxDuSoR0i4382UVae3zEGwS2hvoM2R4"
    }

    object DateFormats{
        const val MONTH_DAY_PATTERN = "dd MMM"
    }

    enum class ProductQueryType(val typeString:String) {
        TITLE(""),LAST_CURSOR("lastCursor")
    }

}
