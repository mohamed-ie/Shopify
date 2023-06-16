package com.example.shopify.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import com.example.shopify.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Provides
    @Singleton
    fun provideApolloClient(interceptor: ApolloInterceptor): ApolloClient =
        ApolloClient.Builder()
            .serverUrl("https://${BuildConfig.DOMAIN}/admin/api/2021-07/graphql.json")
            .addInterceptor(interceptor)
            .build()


    @Provides
    @Singleton
    fun provideApolloInterceptor(): ApolloInterceptor = object : ApolloInterceptor {
        override fun <D : Operation.Data> intercept(
            request: ApolloRequest<D>,
            chain: ApolloInterceptorChain
        ): Flow<ApolloResponse<D>> {
            val newRequest = request.newBuilder()
                .addHttpHeader("X-Shopify-Access-Token", BuildConfig.ADMIN_ACCESS_TOKEN)
                .build()
            return chain.proceed(newRequest)
        }
    }
}