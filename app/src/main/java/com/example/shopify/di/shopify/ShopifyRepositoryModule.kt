package com.example.shopify.di.shopify

import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopifyRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsShopifyRepository(shopifyRepositoryImpl: ShopifyRepositoryImpl): ShopifyRepository
}