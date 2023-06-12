package com.example.shopify.feature.navigation_bar.model.repository

import com.example.shopify.feature.address.addresses.model.MyAccountMinAddress
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow

interface ShopifyRepository {
    fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>>
    fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserInfoResult>>
    suspend fun saveUserInfo(userResponseInfo: SignInUserInfoResult)
    fun getUserInfo(): Flow<SignInUserInfoResult>
    fun isLoggedIn(): Flow<Boolean>
    fun getBrands(): Flow<Resource<List<Brand>?>>
    suspend fun getCart(): Resource<Cart?>
    fun getProductsByBrandName(brandName: String): Flow<Resource<List<BrandProduct>>>
    fun getProductDetailsByID(id: String): Flow<Resource<Product>>
    suspend fun getProductReviewById(productId: String, reviewsCount: Int? = null): List<Review>
    suspend fun setProductReview(productId: String, review: Review)
    suspend fun saveAddress(address: Storefront.MailingAddressInput): Resource<Boolean>
    suspend fun deleteAddress(addressId: ID): Resource<Boolean>
    fun getMinCustomerInfo(): Flow<Resource<MinCustomerInfo>>
    suspend fun updateCurrency(currency: String)
    suspend fun signOut()
    suspend fun getAddresses(): Resource<List<MyAccountMinAddress>>
    suspend fun addToCart(productVariantId: ID, quantity: Int): Resource<String?>
    suspend fun removeCartLines(linesId: List<ID>): Resource<Cart?>
    suspend fun changeCartLineQuantity(merchandiseId: ID, quantity: Int): Resource<Cart?>
    suspend fun applyCouponToCart(coupon: String): Resource<Cart?>
    suspend fun updateCartAddress(addressId: ID): Resource<String?>
}