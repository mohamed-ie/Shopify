package com.example.shopify.helpers.firestore.mapper

import com.example.shopify.ui.product_details.product_details.view.Review
import com.google.firebase.firestore.DocumentSnapshot
import com.shopify.graphql.support.ID

interface FireStoreMapper {
    fun mapSnapShotDocumentsToReview(snapshot: DocumentSnapshot): Review
    fun mapEncodedToDecodedProductId(encodedProductId: String): ID
    fun mapProductIDToEncodedProductId(productId:ID): String
}