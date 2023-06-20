package com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiLayerCurrencyApiClient {
    @GET("live")
    suspend fun getLiveCurrencyExChange(
        @Query("source") source:String,
        @Query("currencies") vararg currencies:String
    ): Currency
}