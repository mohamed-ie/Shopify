package com.example.shopify.model.repository

import com.example.shopify.helpers.Resource
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserResponseInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.example.shopify.ui.screen.home.model.Brand
import kotlinx.coroutines.flow.Flow

interface ShopifyRepository {
    fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>>
    fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserResponseInfo>>
    fun getBrands(): Flow<Resource<List<Brand>?>>
}