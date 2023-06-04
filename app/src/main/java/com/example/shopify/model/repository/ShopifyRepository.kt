package com.example.shopify.model.repository

import com.example.shopify.helpers.Resource
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfoResult
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.example.shopify.ui.screen.home.model.Brand
import com.example.shopify.ui.screen.productDetails.model.Product
import kotlinx.coroutines.flow.Flow

interface ShopifyRepository {
    fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>>
    fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserInfoResult>>
    suspend fun saveUserInfo(userResponseInfo: SignInUserInfoResult)
    fun getUserInfo(): Flow<SignInUserInfoResult>
    fun isLoggedIn(): Flow<Boolean>
    fun getBrands(): Flow<Resource<List<Brand>?>>
    fun getProductDetailsByID(id: String) : Flow<Resource<Product>>
}