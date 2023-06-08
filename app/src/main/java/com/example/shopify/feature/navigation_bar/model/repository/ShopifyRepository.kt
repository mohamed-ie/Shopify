package com.example.shopify.feature.navigation_bar.model.repository

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface ShopifyRepository {
    fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>>
    fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserInfoResult>>
    suspend fun saveUserInfo(userResponseInfo: SignInUserInfoResult)
    fun getUserInfo(): Flow<SignInUserInfoResult>
    fun isLoggedIn(): Flow<Boolean>
    fun getBrands(): Flow<Resource<List<Brand>?>>
    fun getCart(): Flow<Resource<Cart>>
    fun getProductsByBrandName(brandName: String): Flow<Resource<List<BrandProduct>>>
    fun getProductDetailsByID(id: String) : Flow<Resource<Product>>
    suspend fun getProductReviewById(productId: String, reviewsCount: Int? = null): List<Review>
    suspend fun setProductReview(productId: String, review: Review)
}