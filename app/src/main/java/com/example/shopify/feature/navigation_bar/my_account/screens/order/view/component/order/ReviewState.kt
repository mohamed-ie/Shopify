package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order

import com.shopify.graphql.support.ID

data class ReviewState(
    val orderIndex:Int = 0,
    val lineIndex:Int = 0,
    val title:String = "",
    val reviewContent:String = "",
    val rating:Double  = 0.0,
    val isLoading:Boolean = false,
    val visible:Boolean = false
)
