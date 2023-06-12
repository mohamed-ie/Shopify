package com.example.shopify.feature.navigation_bar.model.remote

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.google.firebase.firestore.DocumentSnapshot
import com.shopify.graphql.support.ID

interface FireStoreManager {
    suspend fun getReviewsByProductId(id: String,reviewsCount:Int? = null): List<Review>
    suspend fun setProductReviewByProductId(productId: String, review: Review)
    suspend fun updateCurrency(customerId: String, currency: String)
    suspend fun getCurrency(customerId: String): String
    suspend fun updateWishList(customerId: String, productId: ID)
    suspend fun getWishList(customerId: String): List<ID>
    suspend fun createCustomer(customerId: String)
    suspend fun removeAWishListProduct(customerId: String, productId: ID)
}