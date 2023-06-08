package com.example.shopify.di.shopify


import com.example.shopify.helpers.shopify.input_creator.ShopifyInputCreator
import com.example.shopify.helpers.shopify.input_creator.ShopifyInputCreatorImpl
import com.example.shopify.helpers.shopify.query_generator.ShopifyQueryGenerator
import com.example.shopify.helpers.shopify.query_generator.ShopifyQueryGeneratorImpl
import com.example.shopify.helpers.shopify.mapper.ShopifyMapper
import com.example.shopify.helpers.shopify.mapper.ShopifyMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopifyHelpers {

    @Binds
    @Singleton
    abstract fun bindsShopifyInputCreator(shopifyInputCreatorImpl: ShopifyInputCreatorImpl): ShopifyInputCreator

    @Binds
    @Singleton
    abstract fun bindShopifyMapper(shopifyMapperImpl: ShopifyMapperImpl): ShopifyMapper

    @Binds
    @Singleton
    abstract fun bindShopifyGeneratorQuery(shopifyQueryGeneratorImpl: ShopifyQueryGeneratorImpl): ShopifyQueryGenerator

}