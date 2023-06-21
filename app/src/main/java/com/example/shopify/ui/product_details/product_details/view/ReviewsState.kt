package com.example.shopify.ui.product_details.product_details.view

import com.google.firebase.firestore.FieldValue

data class Review(
    val reviewer:String = "",
    val rate:Double = 0.0,
    val review:String = "",
    val description:String = "",
    val time:String? = "",
    val createdAt:FieldValue? = null
)


data class ReviewsState(
    val reviews:List<Review> = listOf(),
    val reviewCount:Int = 0,
    val ratingCount:Int = 0,
    val averageRating:Double = 0.0
)