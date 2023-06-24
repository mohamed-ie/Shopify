package com.example.shopify.di

import com.example.shopify.data.stripe.StripeApiClient
import com.example.shopify.data.stripe.repository.StripeRepository
import com.example.shopify.data.stripe.repository.StripeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StripeModule {

    @Provides
    @Singleton
    fun provideStripeApiClient(): StripeApiClient =
        Retrofit.Builder()
            .baseUrl(StripeApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StripeApiClient::class.java)


    @Provides
    @Singleton
    fun provideStripeRepository(
        @IODispatcher
        ioDispatcher: CoroutineDispatcher,
        apiClient: StripeApiClient
    ): StripeRepository = StripeRepositoryImpl(apiClient,ioDispatcher)
}