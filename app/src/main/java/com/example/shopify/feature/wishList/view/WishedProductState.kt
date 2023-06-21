package com.example.shopify.feature.wishList.view

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Price
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product

data class WishedProductState(
    val product: Product = Product(),
    val isAddedToCard:Boolean = false,
    val isAddingToCard:Boolean = false,
)

data class WishedBottomSheetState(
    val expandBottomSheet:Boolean = false,
    val isAdded:Boolean = false,
    val isTotalPriceLoaded:Boolean = false,
    val availableQuantity:Int = 0,
    val totalCartPrice: String = "",
    val productTitle:String = ""
)
