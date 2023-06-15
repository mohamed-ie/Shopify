package com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange

import com.example.shopify.feature.navigation_bar.model.local.ShopifyDataStoreManager
import com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency.ApiLayerCurrencyDto
import com.shopify.buy3.Storefront.CurrencyCode
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class ApiLayerExchangeRepositoryImpl @Inject constructor(
    private val dataStoreManager: ShopifyDataStoreManager,
    private val currencyDto: ApiLayerCurrencyDto
) : ApiLayerExchangeRepository {

    override suspend fun updateCurrentLiveCurrencyAmount(){
        val currentCurrencyCode =  dataStoreManager.getCurrency().first()
        setCurrencyAmountPerPound(currentCurrencyCode)
    }

    override suspend fun changeCurrencyCode(currencyCode: String){
        dataStoreManager.setCurrency(currencyCode)
        setCurrencyAmountPerPound(currencyCode)
    }

    private suspend fun setCurrencyAmountPerPound(currencyCode: String){
        val liveAmountPerOnePound = currencyDto
            .getLiveCurrencyExChange(CurrencyCode.EGP.toString(),currencyCode).let {currency ->
                currency.quotes?.get(CurrencyCode.EGP.toString() + currencyCode) ?: 0f
            }
        dataStoreManager.setCurrencyAmountPerOnePound(liveAmountPerOnePound)
    }
}