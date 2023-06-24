package com.example.shopify.ui.order.review

data class ReviewState(
    val orderIndex:Int = 0,
    val lineIndex:Int = 0,
    val title:String = "",
    val reviewContent:String = "",
    val rating:Double  = 0.0,
    val isLoading:Boolean = false,
    val visible:Boolean = false
)
