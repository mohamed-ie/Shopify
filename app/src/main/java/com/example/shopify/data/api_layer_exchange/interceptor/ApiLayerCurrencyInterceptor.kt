package com.example.shopify.data.api_layer_exchange.interceptor

import com.example.shopify.utils.Constants
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class ApiLayerCurrencyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("apikey", Constants.ApiLayerCurrency.API_KEY)
            .build()
        return try {
            chain.proceed(request)
        } catch (exception: Exception) {
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(999)
                .message(exception.localizedMessage ?: "")
                .body("".toResponseBody()).build()
        }

    }
}