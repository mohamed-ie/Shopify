package com.example.shopify.data.api_layer_exchange.repository

import com.example.shopify.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface ApiLayerExchangeRepository {
    val currentCurrency: Flow<String>
    suspend fun updateCurrentLiveCurrencyAmount()
    suspend fun changeCurrencyCode(currencyCode: String): Resource<Unit>
}