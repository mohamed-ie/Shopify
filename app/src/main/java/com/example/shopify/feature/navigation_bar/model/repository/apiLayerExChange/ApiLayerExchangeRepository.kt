package com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange

interface ApiLayerExchangeRepository {
    suspend fun updateCurrentLiveCurrencyAmount()
    suspend fun changeCurrencyCode(currencyCode: String)
}