package com.example.shopify.di.shopify

import android.content.Context
import com.example.shopify.BuildConfig
import com.shopify.buy3.GraphClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShopifySingletonModule {

    @Provides
    @Singleton
    fun provideGraphClient(context: Context): GraphClient =
        GraphClient.build(
            context,
            BuildConfig.DOMAIN,
            BuildConfig.ACCESS_TOKEN
        )

}