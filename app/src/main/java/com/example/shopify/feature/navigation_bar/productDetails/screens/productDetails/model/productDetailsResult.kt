package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model

import com.shopify.graphql.support.ID


data class Product(
    val id:ID = ID(""),
    val image: String = "",
    val description: String = "",
    val totalInventory: Int = 0,
    val variants: List<VariantItem> = listOf(),
    val title: String = "",
    val price: Price = Price("",""),
    val discount: Discount = Discount("",0),
    val vendor: String = "",
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
	val id: String?,
	val price:String?,
	val title: String?
)





