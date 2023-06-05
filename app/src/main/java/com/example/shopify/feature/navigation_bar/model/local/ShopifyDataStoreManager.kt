package com.example.shopify.feature.navigation_bar.model.local

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import kotlinx.coroutines.flow.Flow

interface ShopifyDataStoreManager {
    suspend fun saveUserInfo(signInUserInfoResult: SignInUserInfoResult)
    fun getUserInfo(): Flow<SignInUserInfoResult>
    fun getAccessToken(): Flow<String?>
}