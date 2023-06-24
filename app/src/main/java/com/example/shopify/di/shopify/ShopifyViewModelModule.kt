package com.example.shopify.di.shopify

import com.shopify.buy3.CardClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ShopifyViewModelModule {

    @Provides
    fun provideShopifyCardClient(): CardClient = CardClient()
}