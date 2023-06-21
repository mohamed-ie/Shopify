package com.example.shopify.di.apiLayerCurrency

import com.example.shopify.data.api_layer_exchange.repository.ApiLayerExchangeRepository
import com.example.shopify.data.api_layer_exchange.repository.ApiLayerExchangeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ApiLayerCurrencyRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindApiLayerExchangeRepository(apiLayerExchangeRepositoryImpl: ApiLayerExchangeRepositoryImpl): ApiLayerExchangeRepository
}