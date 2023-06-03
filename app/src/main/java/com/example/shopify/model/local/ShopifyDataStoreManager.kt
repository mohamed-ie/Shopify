package com.example.shopify.model.local

import com.example.shopify.ui.screen.auth.login.model.SignInUserInfoResult
import kotlinx.coroutines.flow.Flow

interface ShopifyDataStoreManager {
    suspend fun saveUserInfo(signInUserInfoResult: SignInUserInfoResult)
    fun getUserInfo(): Flow<SignInUserInfoResult>
    fun getAccessToken(): Flow<String?>
}