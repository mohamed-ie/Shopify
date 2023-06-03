package com.example.shopify.di.shopify

import com.example.shopify.model.repository.generator.ShopifyQueryGenerator
import com.example.shopify.model.repository.generator.ShopifyQueryGeneratorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ShopifyQueryGeneratorModule {

    @Binds
    @Singleton
    abstract fun bindShopifyGeneratorQuery(shopifyQueryGeneratorImpl: ShopifyQueryGeneratorImpl):ShopifyQueryGenerator
}