package com.example.shopify.data.firestore

import com.example.shopify.helpers.Resource
import com.example.shopify.ui.product_details.product_details.view.Review
import com.shopify.graphql.support.ID

interface FireStoreManager {
    suspend fun getReviewsByProductId(id: ID, reviewsCount: Int? = null): Resource<List<Review>>
    suspend fun setProductReviewByProductId(productId: ID, review: Review):Resource<Unit>
    suspend fun updateCurrency(customerId: String, currency: String)
    suspend fun getCurrency(customerId: String):  Resource<String>
    suspend fun updateWishList(customerId: String, productId: ID)
    suspend fun getWishList(customerId: String): Resource<List<ID>>
    suspend fun createCustomer(customerId: String)
    suspend fun removeAWishListProduct(customerId: String, productId: ID)
    suspend fun getCurrentCartId(email: String): Resource<String?>
    suspend fun setCurrentCartId(email: String, cartId: String): Resource<Unit>
    suspend fun clearDraftOrderId(email: String): Resource<Unit>
    suspend fun createUserEmail(email: String): Resource<Unit>
    suspend fun redeemCoupon(coupon: String): Resource<Double?>
}