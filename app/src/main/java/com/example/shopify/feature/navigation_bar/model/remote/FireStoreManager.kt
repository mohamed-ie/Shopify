package com.example.shopify.feature.navigation_bar.model.remote

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.google.firebase.firestore.DocumentSnapshot

interface FireStoreManager {
    suspend fun getReviewsByProductId(id: String,reviewsCount:Int? = null): List<DocumentSnapshot>
    suspend fun setProductReviewByProductId(productId: String, review: Review)
    suspend fun updateCurrency(customerId: String, currency: String)
    suspend fun getCurrency(customerId: String): String
}