package com.example.shopify.ui.wishList.view

import com.example.shopify.model.product_details.Product

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
