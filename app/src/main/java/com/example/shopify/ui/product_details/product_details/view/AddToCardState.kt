package com.example.shopify.ui.product_details.product_details.view


data class AddToCardState (
    val selectedQuantity:Int = 1,
    val isOpened:Boolean = false,
    val expandBottomSheet:Boolean = false,
    val isAdded:Boolean = false,
    val isTotalPriceLoaded:Boolean = false,
    val availableQuantity:Int = 0,
    val totalCartPrice: String = "",
    val sendSelectedQuantity:(Int) ->Unit,
    val openQuantity:()->Unit,
    val closeQuantity:()->Unit,
    val continueShopping:()->Unit,
    val addToCard:()->Unit
)