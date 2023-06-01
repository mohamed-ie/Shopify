package com.example.shopify.di.interceptor

import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.example.shopify.core.model.interceptor.ShopifyApolloInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopifyApolloInterceptorModule {


    @Binds
    @Singleton
    abstract fun bindsShopifyApolloInterceptor(shopifyApolloInterceptor: ShopifyApolloInterceptor): ApolloInterceptor
}