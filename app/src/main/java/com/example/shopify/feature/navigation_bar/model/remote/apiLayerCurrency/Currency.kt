package com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency

import com.google.gson.annotations.SerializedName

data class Currency(
	@field:SerializedName("quotes")
	val quotes: Map<String,Float> ? = null
)
