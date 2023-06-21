package com.example.shopify.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.shopify.model.auth.signin.SignInUserInfoResult
import com.example.shopify.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ShopifyDataStoreManager {
    override suspend fun saveUserInfo(signInUserInfoResult: SignInUserInfoResult) {
        dataStore.edit { userInfoMutablePreference ->
            userInfoMutablePreference[Constants.DataStoreKeys.USER_ACCESS_TOKEN] =
                signInUserInfoResult.accessToken
            userInfoMutablePreference[Constants.DataStoreKeys.USER_EXPIRED_TOKEN_AT] =
                signInUserInfoResult.expireTime.toString()
            userInfoMutablePreference[Constants.DataStoreKeys.USER_EMAIL] =
                signInUserInfoResult.email
            userInfoMutablePreference[Constants.DataStoreKeys.USER_PASSWORD] =
                signInUserInfoResult.password
        }
    }

    override fun getUserInfo(): Flow<SignInUserInfoResult> =
        dataStore.data.map { preference ->
            SignInUserInfoResult(
                preference[Constants.DataStoreKeys.USER_EMAIL] ?: "",
                preference[Constants.DataStoreKeys.USER_PASSWORD] ?: "",
                preference[Constants.DataStoreKeys.USER_ACCESS_TOKEN] ?: "",
                preference[Constants.DataStoreKeys.USER_EXPIRED_TOKEN_AT] ?: "",
                ""
            )
        }

    override fun getAccessToken(): Flow<String> =
        dataStore.data.map { preference ->
            preference[Constants.DataStoreKeys.USER_ACCESS_TOKEN] ?: ""
        }

    override fun getEmail(): Flow<String> =
        dataStore.data.map { it[Constants.DataStoreKeys.USER_EMAIL]?.lowercase() ?: "" }

    override fun getCurrency(): Flow<String> =
        dataStore.data.map { it[Constants.DataStoreKeys.CURRENCY] ?: "EGP" }

    override suspend fun setEmail(email: String) {
        dataStore.edit { it[Constants.DataStoreKeys.USER_EMAIL] = email }
    }

    override suspend fun clearAccessToken() {
        dataStore.edit { it[Constants.DataStoreKeys.USER_ACCESS_TOKEN] = "" }
    }

    override suspend fun setCurrency(currency: String) {
        dataStore.edit { it[Constants.DataStoreKeys.CURRENCY] = currency }
    }


    override suspend fun setCurrencyAmountPerOnePound(currencyAmount: Float) {
        dataStore.edit { it[Constants.DataStoreKeys.CURRENCY_AMOUNT] = currencyAmount.toString() }
    }


    override fun getCurrencyAmountPerOnePound(): Flow<Float> =
        dataStore.data.map { it[Constants.DataStoreKeys.CURRENCY_AMOUNT]?.toFloat() ?: 0f }

    override fun getCustomerId(): Flow<String> =
        dataStore.data.map { it[Constants.DataStoreKeys.CUSTOMER_ID] ?: "" }


    override suspend fun setCustomerId(customerId: String) {
        dataStore.edit { it[Constants.DataStoreKeys.CUSTOMER_ID] = customerId }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}