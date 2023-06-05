package com.example.shopify.feature.navigation_bar.productDetails.model



data class Product(
    val image: String = "",
    val isGiftCard: Boolean = false,
    val description: String = "",
    val totalInventory: Int = 0,
    val variants: List<VariantItem> = listOf(),
    val title: String = "",
    val price: Price = Price("",""),
    val discount: Discount = Discount("",0),
    val requiresSellingPlan: Boolean = false,
    val tags: List<String> = listOf(),
    val createdAt: String = "",
    val vendor: String = "",
    val onlineStoreUrl: String = "",
    val productType: String =""
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





