package com.example.shopify.model.product_details

import com.shopify.graphql.support.ID


data class Product(
    val id:ID = ID(""),
    val images: List<String> = listOf(),
    val description: String = "",
    val totalInventory: Int = 0,
    val variants: List<VariantItem> = listOf(),
    val title: String = "",
    val price: Price = Price("",""),
    val discount: Discount = Discount("",0),
    val vendor: String = "",
    val isFavourite:Boolean = false,
    val isLogged:Boolean = false
)


data class Price(
	val amount:String,
	val currencyCode:String
)

data class Discount(
	val realPrice:String,
	val percent:Int
)

data class VariantItem(
    val image: String?,
    val price: String?,
    val title: String?,
    val availableQuantity:Int,
    val id: ID? = null
)





