package com.example.shopify.di.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.example.shopify.feature.core.domain.interceptor.ShopifyApolloInterceptor
import com.example.shopify.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Singleton
    @Provides
    fun provideApolloClient(shopifyApolloInterceptor: ApolloInterceptor) : ApolloClient =
        ApolloClient.Builder()
            .serverUrl(Constants.SHOPIFY.BASE_URL)
            .addInterceptor(shopifyApolloInterceptor)
            .build()

}