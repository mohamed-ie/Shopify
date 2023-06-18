package com.example.shopify.feature.navigation_bar.model.repository.shopify

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.common.model.Pageable
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.example.shopify.utils.Constants
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.MailingAddress
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow

interface ShopifyRepository {
    fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>>
    fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserInfoResult>>
    suspend fun saveUserInfo(userResponseInfo: SignInUserInfoResult)
    fun getUserInfo(): Flow<SignInUserInfoResult>
    fun isLoggedIn(): Flow<Boolean>
    fun getBrands(): Flow<Resource<List<Brand>>>
    suspend fun getCart(): Resource<Cart?>
    suspend fun getProductsByBrandName(brandName: String): Resource<List<BrandProduct>>
    fun getProductDetailsByID(id: String): Flow<Resource<Product>>
    suspend fun getProductReviewById(productId: ID, reviewsCount: Int? = null): List<Review>
    suspend fun setProductReview(productId: ID, review: Review)
    suspend fun getCheckOutId(cart: Cart): Flow<Resource<ID?>>
    fun getProductsCategory(
        productType: String,
        productTag: String
    ): Flow<Resource<List<BrandProduct>>>
    fun getProductsTag(): Flow<Resource<List<String>>>
    fun getProductsType(): Flow<Resource<List<String>>>
    suspend fun saveAddress(address: Storefront.MailingAddressInput): Resource<Boolean>
    suspend fun deleteAddress(addressId: ID): Resource<Boolean>
    fun getMinCustomerInfo(): Flow<Resource<MinCustomerInfo>>
    suspend fun updateCurrency(currency: String)
    suspend fun signOut()
    suspend fun getAddresses(): Resource<List<MailingAddress>>
    suspend fun addToCart(productVariantId: String, quantity: Int): Resource<String?>
    suspend fun applyCouponToCart(coupon: String): Resource<Cart?>
    suspend fun updateCartShippingAddress(address: MailingAddress): Resource<String?>
    fun getShopifyProductsByWishListIDs(): Flow<Resource<Product?>>
    suspend fun addProductWishListById(productId: ID)
    suspend fun removeProductWishListById(productId: ID)
    suspend fun getProductsByQuery(
        productQueryType: Constants.ProductQueryType,
        queryContent: String
    ): Resource<Pageable<List<BrandProduct>>?>

    suspend fun getOrders(): Flow<Resource<List<Order>>>

    suspend fun removeCartLines(productVariantId: String): Resource<Cart?>
    suspend fun changeCartLineQuantity(merchandiseId: String, quantity: Int): Resource<Cart?>
    suspend fun completeOrder(paymentPending: Boolean) :Resource<String?>
    suspend fun sendCompletePayment(): Resource<Pair<String?, String?>?>
}