package com.example.shopify.feature.navigation_bar.model.remote.fireStore

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.shopify.graphql.support.ID

interface FireStoreManager {
    suspend fun getReviewsByProductId(id: ID, reviewsCount: Int? = null): List<Review>
    suspend fun setProductReviewByProductId(productId: ID, review: Review)
    suspend fun updateCurrency(customerId: String, currency: String)
    suspend fun getCurrency(customerId: String): String
    suspend fun updateWishList(customerId: String, productId: ID)
    suspend fun getWishList(customerId: String): List<ID>
    suspend fun createCustomer(customerId: String)
    suspend fun removeAWishListProduct(customerId: String, productId: ID)
    suspend fun getCurrentCartId(email: String): Resource<String?>
    suspend fun setCurrentCartId(email: String, cartId: String): Resource<Void>
    suspend fun clearDraftOrderId(email: String): Resource<Void>
}