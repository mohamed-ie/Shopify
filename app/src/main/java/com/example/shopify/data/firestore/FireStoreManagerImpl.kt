package com.example.shopify.data.firestore

import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIError
import com.example.shopify.helpers.firestore.mapper.FireStoreMapper
import com.example.shopify.helpers.firestore.mapper.encodeProductId
import com.example.shopify.ui.product_details.product_details.view.Review
import com.google.android.gms.tasks.Task
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

    object Coupons{
        const val PATH = "coupons"
        object Field{
            const val VALUE ="value"
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
    ): Resource<List<Review>> =
        fireStore.collection(PATH)
            .document(id.encodeProductId())
            .collection(REVIEW_PATH)
            .get()
            .awaitResource {
                it.documents
                    .map(mapper::mapSnapShotDocumentsToReview)
            }


    override suspend fun setProductReviewByProductId(
        productId: ID,
        review: Review
    ): Resource<Unit> =
        fireStore.collection(PATH)
            .document(productId.encodeProductId())
            .collection(REVIEW_PATH)
            .document(review.reviewer)
            .set(review.copy(createdAt = FieldValue.serverTimestamp(), time = null))
            .awaitResource {}


    override suspend fun updateCurrency(customerId: String, currency: String) {
        customerId.ifEmpty { return }
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .set(Collections.singletonMap(Customer.Fields.CURRENCY, currency))
            .awaitResource {}
    }

    override suspend fun getCurrency(customerId: String): Resource<String> {
        customerId.ifEmpty { return Resource.Success("EGP") }
        return fireStore.collection(Customer.PATH)
            .document(customerId)
            .get()
            .awaitResource { it.get(Customer.Fields.CURRENCY) as String }

    }

    override suspend fun createUserEmail(email: String): Resource<Unit> {
        email.ifEmpty { return Resource.Success(Unit) }
        return fireStore.collection(Customer.PATH)
            .document(email.lowercase())
            .set(mapOf(Customer.Fields.WISH_LIST to emptyList<String>()))
            .awaitResource {}
    }

    override suspend fun setCurrentCartId(email: String, cartId: String): Resource<Unit> {
        email.ifEmpty { return Resource.Success(Unit) }
        return fireStore.collection(Customer.PATH)
            .document(email)
            .update(Customer.Fields.CURRENT_CART_ID, cartId)
            .awaitResource {}
    }

    override suspend fun clearDraftOrderId(email: String): Resource<Unit> {
        email.ifEmpty { return Resource.Success(Unit) }
        return fireStore.collection(Customer.PATH)
            .document(email)
            .update(Customer.Fields.CURRENT_CART_ID, null)
            .awaitResource {}

    }

    override suspend fun getCurrentCartId(email: String): Resource<String?> {
        email.ifEmpty { return Resource.Success(null) }
        return fireStore.collection(Customer.PATH)
            .document(email)
            .get()
            .awaitResource {
                it.get(Customer.Fields.CURRENT_CART_ID) as String?
            }

    }


    override suspend fun updateWishList(customerId: String, productId: ID) {
        customerId.ifEmpty { return }
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .update(
                Customer.Fields.WISH_LIST,
                FieldValue.arrayUnion(mapper.mapProductIDToEncodedProductId(productId))
            ).awaitResource()
    }

    override suspend fun removeAWishListProduct(customerId: String, productId: ID) {
        customerId.ifEmpty { return }
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .update(
                Customer.Fields.WISH_LIST,
                FieldValue.arrayRemove(mapper.mapProductIDToEncodedProductId(productId))
            )
            .awaitResource()
    }

    override suspend fun createCustomer(customerId: String) {
        val customerMap = mapOf(Customer.Fields.CURRENCY to "EGP")
        fireStore.collection(Customer.PATH)
            .document(customerId)
            .set(customerMap)
            .awaitResource()
    }

    override suspend fun getWishList(customerId: String): Resource<List<ID>> {
        customerId.ifEmpty { return Resource.Success(listOf()) }
        return fireStore.collection(Customer.PATH)
            .document(customerId)
            .get()
            .awaitResource { documentSnapshot ->
                (documentSnapshot.get(Customer.Fields.WISH_LIST) as? List<*>)
                    ?.map { it as String }
                    ?.map(mapper::mapEncodedToDecodedProductId) ?: emptyList()
            }
    }

    override suspend fun redeemCoupon(coupon: String): Resource<Double?> {
        return fireStore.collection(Coupons.PATH)
            .document(coupon)
            .get()
            .awaitResource {
            val value = it.get(Coupons.Field.VALUE)
                        value as Double?
            }
    }

    private suspend fun <I, O> Task<I>.awaitResource(block: suspend (I) -> O): Resource<O> {
        return try {
            Resource.Success(block(await()))
        } catch (e: Exception) {
            Resource.Error(UIError.Unexpected)
        }
    }

    private suspend fun <I> Task<I>.awaitResource(): Resource<I> {
        return try {
            Resource.Success(await())
        } catch (e: Exception) {
            Resource.Error(UIError.Unexpected)
        }
    }

}