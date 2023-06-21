package com.example.shopify.data.api_layer_exchange.api_client

import com.example.shopify.data.api_layer_exchange.response.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiLayerCurrencyApiClient {
    @GET("live")
    suspend fun getLiveCurrencyExChange(
        @Query("source") source:String,
        @Query("currencies") vararg currencies:String
    ): CurrencyResponse
}