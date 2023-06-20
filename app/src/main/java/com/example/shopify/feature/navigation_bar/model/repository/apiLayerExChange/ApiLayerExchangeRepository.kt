package com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange

import com.example.shopify.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface ApiLayerExchangeRepository {
    val currentCurrency: Flow<String>
    suspend fun updateCurrentLiveCurrencyAmount()
    suspend fun changeCurrencyCode(currencyCode: String): Resource<Unit>
}