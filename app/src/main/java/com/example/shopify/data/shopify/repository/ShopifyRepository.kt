package com.example.shopify.data.shopify.repository

import com.example.shopify.helpers.Resource
import com.example.shopify.model.Pageable
import com.example.shopify.model.auth.signin.SignInUserInfo
import com.example.shopify.model.auth.signin.SignInUserInfoResult
import com.example.shopify.model.auth.signup.SignUpUserInfo
import com.example.shopify.model.auth.signup.SignUpUserResponseInfo
import com.example.shopify.model.cart.cart.Cart
import com.example.shopify.model.cart.order.Order
import com.example.shopify.model.home.Brand
import com.example.shopify.model.my_account.MinCustomerInfo
import com.example.shopify.model.product_details.Product
import com.example.shopify.ui.bottom_bar.home.product.model.BrandProduct
import com.example.shopify.ui.product_details.product_details.view.Review
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
    suspend fun setProductReview(productId: ID, review: Review):Resource<Unit>
    suspend fun getCheckOutId(cart: Cart): Flow<Resource<ID?>>
    fun getProductsCategory(
        productType: String,
        productTag: String
    ): Flow<Resource<List<BrandProduct>>>

    suspend fun getProductsTag(): Resource<List<String>>
    suspend fun getProductsType(): Resource<List<String>>
    suspend fun saveAddress(address: Storefront.MailingAddressInput): Resource<String?>
    suspend fun deleteAddress(addressId: ID): Resource<Boolean>
    suspend fun getMinCustomerInfo(): Resource<MinCustomerInfo>
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
    suspend fun completeOrder(paymentPending: Boolean): Resource<String?>
    suspend fun sendCompletePayment(): Resource<Pair<String?, String?>?>
    suspend fun changePassword(password: String): Resource<String?>
    suspend fun changePhoneNumber(phone: String): Resource<String?>
    suspend fun changeName(firstName: String, lastName: String): Resource<String?>
    suspend fun createUserEmail(email: String): Resource<Unit>
    suspend fun updateAddress(
        addressId: ID,
        address: Storefront.MailingAddressInput
    ): Resource<String?>
}