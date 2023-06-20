package com.example.shopify.di.apiLayerCurrency

import com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency.ApiLayerCurrencyApiClient
import com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency.ApiLayerCurrencyInterceptor
import com.example.shopify.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiLayerCurrencySingletonModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.ApiLayerCurrency.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: ApiLayerCurrencyInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideOneCallDto(retrofit: Retrofit): ApiLayerCurrencyApiClient =
        retrofit.create(ApiLayerCurrencyApiClient::class.java)


}