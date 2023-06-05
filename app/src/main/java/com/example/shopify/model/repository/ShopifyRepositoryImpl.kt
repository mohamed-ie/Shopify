package com.example.shopify.model.repository

import com.example.shopify.helpers.Resource
import com.example.shopify.model.local.ShopifyDataStoreManager
import com.example.shopify.model.repository.generator.ShopifyQueryGenerator
import com.example.shopify.model.repository.mapper.ShopifyMapper
import com.example.shopify.ui.screen.Product.model.Product
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfoResult
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.example.shopify.ui.screen.home.model.Brand
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
    private val dataStoreManager: ShopifyDataStoreManager,
    private val defaultDispatcher: CoroutineDispatcher
) : ShopifyRepository {

    override fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>> {
       return queryGenerator.generateSingUpQuery(userInfo)
           .enqueue()
           .mapResource(mapper::map)
    }

    override fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserInfoResult>> {
        return queryGenerator.generateSingInQuery(userInfo)
            .enqueue()
            .mapResource { response -> mapper.mapToSignInResponse(response, userInfo) }
    }

    override suspend fun saveUserInfo(userResponseInfo: SignInUserInfoResult) =
        dataStoreManager.saveUserInfo(userResponseInfo)


    override fun getUserInfo(): Flow<SignInUserInfoResult> =
        dataStoreManager.getUserInfo()


    override fun isLoggedIn(): Flow<Boolean> =
        dataStoreManager.getAccessToken()
            .map { it != null }
            .flowOn(defaultDispatcher)

    override fun getBrands(): Flow<Resource<List<Brand>?>> {
        val query = queryGenerator.generateBrandQuery()
        return query!!.enqueue().mapResource(mapper::mapToBrandResponse)
    }

    override fun getProductsByBrandName(brandName: String): Flow<Resource<List<Product>>> {
        val query = queryGenerator.generateProductByBrandQuery(brandName)
        return query!!.enqueue().mapResource(mapper::mapToProductsByBrandResponse)
    }

    private fun Storefront.QueryRootQuery.enqueue() = callbackFlow {
        val call = graphClient.queryGraph(this@enqueue).enqueue { result ->
            when (result) {
                is GraphCallResult.Success ->
                    trySend(Resource.Success(result.response))

                is GraphCallResult.Failure ->
                    trySend(Resource.Error(mapper.map(result.error)))

            }
        }
        awaitClose { call.cancel() }
    }

    private fun Storefront.MutationQuery.enqueue() = callbackFlow {
        val call = graphClient.mutateGraph(this@enqueue).enqueue { result ->
            when (result) {
                is GraphCallResult.Success ->
                    trySend(Resource.Success(result.response))

                is GraphCallResult.Failure ->
                    trySend(Resource.Error(mapper.map(result.error)))

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