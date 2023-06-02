package com.example.shopify.model.repository

import com.example.shopify.helpers.Resource
import com.example.shopify.model.repository.generator.ShopifyQueryGenerator
import com.example.shopify.model.repository.mapper.ShopifyMapper
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserResponseInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ShopifyRepositoryImpl @Inject constructor(
    private val graphClient: GraphClient,
    private val queryGenerator: ShopifyQueryGenerator,
    private val mapper: ShopifyMapper,
    private val defaultDispatcher: CoroutineDispatcher
) : ShopifyRepository {

    override fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>> {
        val query = queryGenerator.generateSingUpQuery(userInfo)
        return enqueueAuth(query).mapResource(mapper::map)
    }

    override fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserResponseInfo>> {
        val query = queryGenerator.generateSingInQuery(userInfo)
        return enqueueAuth(query).mapResource(mapper::mapToSignInResponse)
    }


    private fun Storefront.QueryRootQuery.enqueue() = callbackFlow {
        val call = graphClient.queryGraph(this@enqueue).enqueue { result ->
            when (result) {
                is GraphCallResult.Success ->
                    trySend(Resource.Success(result.response))

                is GraphCallResult.Failure ->
                    trySend(Resource.Error(result.error))

            }
        }
        awaitClose { call.cancel() }
    }

    private fun enqueueAuth(query: Storefront.MutationQuery) = callbackFlow {
        val call = graphClient.mutateGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success ->
                    trySend(Resource.Success(result.response))

                is GraphCallResult.Failure ->
                    trySend(Resource.Error(result.error))

            }
        }
        awaitClose { call.cancel() }
    }

    private fun <I, O> Flow<Resource<I>>.mapResource(transform: (I) -> O): Flow<Resource<O>> {
        return map { resource ->
            when (resource) {
                is Resource.Error -> resource
                is Resource.Success -> Resource.Success(transform(resource.data))
            }
        }.flowOn(defaultDispatcher)
    }

}