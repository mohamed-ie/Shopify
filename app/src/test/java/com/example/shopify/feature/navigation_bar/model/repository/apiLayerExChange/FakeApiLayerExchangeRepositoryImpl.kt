package com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange

import com.example.shopify.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeApiLayerExchangeRepositoryImpl : ApiLayerExchangeRepository {
    private val _currentCurrency = MutableStateFlow("EGP")
    override val currentCurrency: Flow<String> = _currentCurrency

    override suspend fun updateCurrentLiveCurrencyAmount() {

    }

    override suspend fun changeCurrencyCode(currencyCode: String): Resource<Unit> {
        _currentCurrency.emit(currencyCode)
        return Resource.Success(Unit)
    }

}