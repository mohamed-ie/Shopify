package com.example.shopify.data.api_layer_exchange.response

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
	@field:SerializedName("quotes")
	val quotes: Map<String,Float> ? = null
)
