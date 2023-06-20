package com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange

import com.example.shopify.di.IODispatcher
import com.example.shopify.feature.navigation_bar.model.local.ShopifyDataStoreManager
import com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency.ApiLayerCurrencyApiClient
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIError
import com.example.shopify.helpers.mapSuspendResource
import com.shopify.buy3.Storefront.CurrencyCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ApiLayerExchangeRepositoryImpl @Inject constructor(
    private val dataStoreManager: ShopifyDataStoreManager,
    private val currencyApiClient: ApiLayerCurrencyApiClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ApiLayerExchangeRepository {

    override val currentCurrency: Flow<String> = dataStoreManager.getCurrency()

    override suspend fun updateCurrentLiveCurrencyAmount() {
        val currentCurrencyCode = dataStoreManager.getCurrency().first()
        setCurrencyAmountPerPound(currentCurrencyCode)
    }

    override suspend fun changeCurrencyCode(currencyCode: String): Resource<Unit> {
        return setCurrencyAmountPerPound(currencyCode)
            .mapSuspendResource {
                dataStoreManager.setCurrency(currencyCode)
            }
    }

    private suspend fun setCurrencyAmountPerPound(currencyCode: String): Resource<Unit> {
        val response = withContext(ioDispatcher) {
            currencyApiClient.getLiveCurrencyExChange(CurrencyCode.EGP.toString(), currencyCode)
        }

        val liveAmount = response.quotes?.get(CurrencyCode.EGP.toString() + currencyCode)
            ?: return Resource.Error(UIError.Unexpected)

        dataStoreManager.setCurrencyAmountPerOnePound(liveAmount)

        return Resource.Success(Unit)
    }
}