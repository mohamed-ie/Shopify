package com.example.shopify.data.data_store

import com.example.shopify.model.auth.signin.SignInUserInfoResult
import kotlinx.coroutines.flow.Flow

interface ShopifyDataStoreManager {
    suspend fun saveUserInfo(signInUserInfoResult: SignInUserInfoResult)
    fun getUserInfo(): Flow<SignInUserInfoResult>
    fun getAccessToken(): Flow<String>
    fun getEmail(): Flow<String>
    fun getCurrency(): Flow<String>
    suspend fun setEmail(email: String)
    suspend fun clearAccessToken()
    suspend fun setCurrency(currency: String)
    suspend fun setCurrencyAmountPerOnePound(currencyAmount: Float)
    fun getCurrencyAmountPerOnePound(): Flow<Float>
    fun getCustomerId(): Flow<String>
    suspend fun setCustomerId(customerId: String)

    suspend fun clear()


}