package com.example.shopify.feature.navigation_bar.model.remote

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.firestore.mapper.FireStoreMapper
import com.example.shopify.helpers.firestore.mapper.encodeProductId
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.shopify.graphql.support.ID
import kotlinx.coroutines.tasks.await
import java.util.Collections
import javax.inject.Inject

class FireStoreManagerImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val mapper: FireStoreMapper,
) : FireStoreManager {
    object Customer {
        const val PATH: String = "customer"

        object Fields {
            const val CURRENCY: String = "currency"
            const val CURRENT_CART_ID: String = "current_cart_id"
            const val WISH_LIST: String = "wishlist"
        }
    }

    companion object Product {
        const val PATH = "product"
        const val REVIEW_PATH = "Review"

        object Field {
            const val CREATED_AT_REVIEW = "createdAt"
            const val DESCRIPTION_REVIEW = "description"
            const val RATE_REVIEW = "rate"
            const val REVIEW_CONTENT = "review"
            const val REVIEWER_REVIEW = "reviewer"
        }
    }

    override suspend fun getReviewsByProductId(
        id: ID,
        reviewsCount: Int?
    ): List<Review> =
        fireStore.collection(PATH)
            .document(id.encodeProductId())
            .collection(REVIEW_PATH)
            .get()
            .await()
            .documents
            .map(mapper::mapSnapShotDocumentsToReview)


    override suspend fun setProductReviewByProductId(productId: ID, review: Review) {
        fireStore.collection(PATH)
            .document(productId.encodeProductId())
            .collection(REVIEW_PATH)
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

    override suspend fun setCurrentCartId(customerId: String, cartId: String) {
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .set(Collections.singletonMap(Customer.Fields.CURRENT_CART_ID, cartId))
            .await()
    }

    override suspend fun getCurrentCartId(email: String): String? {
        return fireStore.collection(Customer.PATH)
            .document(email)
            .get()
            .await()
            .get(Customer.Fields.CURRENT_CART_ID) as String?
    }


    override suspend fun updateWishList(customerId: String, productId: ID) {
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .update(
                Customer.Fields.WISH_LIST,
                FieldValue.arrayUnion(mapper.mapProductIDToEncodedProductId(productId))
            )
            .await()
    }

    override suspend fun removeAWishListProduct(customerId: String, productId: ID) {
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .update(
                Customer.Fields.WISH_LIST,
                FieldValue.arrayRemove(mapper.mapProductIDToEncodedProductId(productId))
            )
            .await()
    }

    override suspend fun createCustomer(customerId: String) {
        val customerMap = mapOf(Customer.Fields.CURRENCY to "EGP")
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .set(customerMap)
            .await()
    }

    override suspend fun getWishList(customerId: String): List<ID> {
        return (fireStore.collection(Customer.PATH)
            .document(customerId)
            .get()
            .await()
            .get(Customer.Fields.WISH_LIST) as? List<*>)
            ?.map { it as String }
            ?.map(mapper::mapEncodedToDecodedProductId) ?: emptyList()
    }
}