package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view

import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Price


data class AddToCardState (
    val selectedQuantity:Int = 1,
    val isOpened:Boolean = false,
    val expandBottomSheet:Boolean = false,
    val isAdded:Boolean = false,
    val isTotalPriceLoaded:Boolean = false,
    val availableQuantity:Int = 0,
    val totalCartPrice: Price = Price("",""),
    val sendSelectedQuantity:(Int) ->Unit,
    val openQuantity:()->Unit,
    val closeQuantity:()->Unit,
    val continueShopping:()->Unit,
    val addToCard:()->Unit
)