package com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency

import com.example.shopify.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiLayerCurrencyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("apikey",Constants.ApiLayerCurrency.API_KEY)
            .build()
        return chain.proceed(request)
    }
}