package com.example.shopify.di.shopify

import com.example.shopify.model.repository.mapper.ShopifyMapper
import com.example.shopify.model.repository.mapper.ShopifyMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ShopifyMapperModule {

    @Binds
    @Singleton
    abstract fun bindShopifyMapper(shopifyMapperImpl: ShopifyMapperImpl): ShopifyMapper
}