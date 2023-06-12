package com.example.shopify.helpers.firestore.mapper

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.google.firebase.firestore.DocumentSnapshot
import com.shopify.graphql.support.ID

interface FireStoreMapper {
    fun mapSnapShotDocumentsToReview(snapshot: DocumentSnapshot): Review
    fun mapEncodedToDecodedProductId(encodedProductId: String): ID
    fun mapProductIDToEncodedProductId(productId:ID): String
}