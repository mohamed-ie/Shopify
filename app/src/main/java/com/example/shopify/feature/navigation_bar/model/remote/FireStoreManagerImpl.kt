package com.example.shopify.feature.navigation_bar.model.remote

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.shopify.mapper.ShopifyMapper
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import java.util.Collections
import javax.inject.Inject

class FireStoreManagerImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val mapper: ShopifyMapper,
    private val defaultDispatcher: CoroutineDispatcher
) : FireStoreManager {
    object Customer {
        const val PATH: String = "customer"

        object Fields {
            const val CURRENCY: String = "currency"
        }
    }

    override suspend fun getReviewsByProductId(
        id: String,
        reviewsCount: Int?
    ): List<DocumentSnapshot> =
        fireStore.collection(FireStore.PRODUCT_COLLECTION_PATH)
            .document(id)
            .collection(FireStore.REVIEW_COLLECTION_PATH)
            .get()
            .await()
            .documents


    override suspend fun setProductReviewByProductId(productId: String, review: Review) {
        fireStore.collection(FireStore.PRODUCT_COLLECTION_PATH)
            .document(productId)
            .collection(FireStore.REVIEW_COLLECTION_PATH)
            .document(review.reviewer)
            .set(review.copy(createdAt = FieldValue.serverTimestamp(), time = null))
            .await()

    }

    override suspend fun updateCurrency(customerId: String, currency: String) {
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .set(Collections.singletonMap(Customer.Fields.CURRENCY, currency))
            .await()
    }

    override suspend fun getCurrency(customerId: String): String {
       return fireStore.collection(Customer.PATH)
            .document(customerId)
            .get()
            .await()
            .get(Customer.Fields.CURRENCY) as String
    }
}


