package com.example.shopify.ui.screen.productDetails.view

import com.example.shopify.ui.screen.productDetails.model.Price

data class AddToCardState (
    val selectedQuantity:Int = 1,
    val isOpened:Boolean = false,
    val expandBottomSheet:Boolean = false,
    val isAdded:Boolean = false,
    val totalCartPrice:Price = Price("",""),
    val sendSelectedQuantity:(Int) ->Unit,
    val openQuantity:()->Unit,
    val closeQuantity:()->Unit,
    val continueShopping:()->Unit,
    val addToCard:()->Unit
)