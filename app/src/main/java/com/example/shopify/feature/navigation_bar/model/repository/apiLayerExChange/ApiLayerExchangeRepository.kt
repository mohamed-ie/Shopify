package com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange

import kotlinx.coroutines.flow.Flow

interface ApiLayerExchangeRepository {
    suspend fun updateCurrentLiveCurrencyAmount()
    suspend fun changeCurrencyCode(currencyCode: String)
    suspend fun getLiveCurrencyExchange(currencyCode: String): Flow<Float>
}