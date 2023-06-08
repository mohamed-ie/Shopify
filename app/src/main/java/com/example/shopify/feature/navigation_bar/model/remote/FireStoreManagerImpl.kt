package com.example.shopify.feature.navigation_bar.model.remote

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.shopify.mapper.ShopifyMapper
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FireStoreManagerImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val mapper: ShopifyMapper,
    private val defaultDispatcher: CoroutineDispatcher
) : FireStoreManager {

    override suspend fun getReviewsByProductId(id:String,reviewsCount:Int?): List<DocumentSnapshot> =
        fireStore.collection(FireStore.PRODUCT_COLLECTION_PATH)
            .document(id)
            .collection(FireStore.REVIEW_COLLECTION_PATH)
            .get()
            .await()
            .documents



    override suspend fun setProductReviewByProductId(productId:String,review: Review) {
        fireStore.collection(FireStore.PRODUCT_COLLECTION_PATH)
            .document(productId)
            .collection(FireStore.REVIEW_COLLECTION_PATH)
            .document(review.reviewer)
            .set(review.copy(createdAt = FieldValue.serverTimestamp(), time = null))
            .await()

    }
}



object FireStore{
    const val PRODUCT_COLLECTION_PATH = "product"
    const val REVIEW_COLLECTION_PATH = "Review"
    const val CREATED_AT_REVIEW_FIELD_KEY = "createdAt"
    const val DESCRIPTION_REVIEW_FIELD_KEY = "description"
    const val RATE_REVIEW_FIELD_KEY = "rate"
    const val REVIEW_CONTENT_REVIEW_FIELD_KEY = "review"
    const val REVIEWER_REVIEW_FIELD_KEY = "reviewer"
}