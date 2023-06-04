package com.example.shopify.ui.screen.productDetails.model



data class Product(
	val images: List<String?>,
	val isGiftCard: Boolean,
	val description: String,
	val totalInventory: Int,
	val variants: List<VariantItem>,
	val title: String,
	val requiresSellingPlan: Boolean,
	val tags: List<String>,
	val createdAt: String,
	val vendor: String,
	val onlineStoreUrl: String,
	val productType: String
)

data class VariantItem(
	val image: String?,
	val id: String?,
	val price:String?,
	val title: String?
)





