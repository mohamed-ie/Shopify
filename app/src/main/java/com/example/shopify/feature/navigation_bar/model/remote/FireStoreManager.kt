package com.example.shopify.feature.navigation_bar.model.remote

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface FireStoreManager {
    suspend fun getReviewsByProductId(id: String,reviewsCount:Int? = null): List<DocumentSnapshot>
    suspend fun setProductReviewByProductId(productId: String, review: Review)
}