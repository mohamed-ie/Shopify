package com.example.shopify.core.model.interceptor

import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import com.example.shopify.BuildConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShopifyApolloInterceptor @Inject constructor() : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val newRequest = request.newBuilder()
            .addHttpHeader("X-Shopify-Access-Token", BuildConfig.ACCESS_TOKEN)
            .build()
        return chain.proceed(newRequest)
    }
}