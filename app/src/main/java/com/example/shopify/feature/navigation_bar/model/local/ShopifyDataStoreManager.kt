package com.example.shopify.feature.navigation_bar.model.local

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
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
}