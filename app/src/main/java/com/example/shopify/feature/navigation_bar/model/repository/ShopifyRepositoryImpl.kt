package com.example.shopify.feature.navigation_bar.model.repository

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.model.local.ShopifyDataStoreManager
import com.example.shopify.feature.navigation_bar.model.remote.FireStoreManager
import com.example.shopify.feature.navigation_bar.my_account.screens.addresses.model.MyAccountMinAddress
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.shopify.mapper.ShopifyMapper
import com.example.shopify.helpers.shopify.query_generator.ShopifyQueryGenerator
import com.example.shopify.utils.enqueue
import com.example.shopify.utils.enqueue1
import com.example.shopify.utils.mapResource
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ShopifyRepositoryImpl @Inject constructor(
    private val graphClient: GraphClient,
    private val queryGenerator: ShopifyQueryGenerator,
    private val mapper: ShopifyMapper,
    private val dataStoreManager: ShopifyDataStoreManager,
    private val fireStoreManager: FireStoreManager,
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
            .map { it?.isNotBlank() ?: false }
            .flowOn(defaultDispatcher)

    override fun getBrands(): Flow<Resource<List<Brand>?>> {
        val query = queryGenerator.generateBrandQuery()
        return query!!.enqueue().mapResource(mapper::mapToBrandResponse)
    }

    override fun getCart(): Flow<Resource<Cart>> = flow {
    }

    override fun getProductsByBrandName(brandName: String): Flow<Resource<List<BrandProduct>>> {
        val query = queryGenerator.generateProductByBrandQuery(brandName)
        return query!!.enqueue().mapResource(mapper::mapToProductsByBrandResponse)
    }

    override fun getProductDetailsByID(id: String): Flow<Resource<Product>> =
        queryGenerator.generateProductDetailsQuery(id)
            .enqueue()
            .mapResource(mapper::mapToProduct)

    override suspend fun getProductReviewById(productId: String, reviewsCount: Int?) =
        withContext(defaultDispatcher) {
            fireStoreManager.getReviewsByProductId(productId, reviewsCount)
                .let { documentSnapshots ->
                    mapper.mapSnapShotDocumentToReview(
                        documentSnapshots.take(
                            reviewsCount ?: documentSnapshots.count()
                        )
                    )
                }
        }

    override suspend fun updateCurrency(currency: String) {
        dataStoreManager.setCurrency(currency)
    }

    override suspend fun setProductReview(productId: String, review: Review) =
        fireStoreManager.setProductReviewByProductId(productId, review)


    override suspend fun saveAddress(address: Storefront.MailingAddressInput): Resource<Boolean> {
        val accessToken = dataStoreManager.getAccessToken().first()

        return queryGenerator.generateCreateAddress(accessToken, address)
            .enqueue1()
            .mapResource(mapper::isAddressSaved)
    }

    override suspend fun deleteAddress(addressId: String): Resource<Boolean> {
        val accessToken = dataStoreManager.getAccessToken().first()

        return queryGenerator.generateDeleteAddressQuery(addressId, accessToken)
            .enqueue1()
            .mapResource(mapper::isAddressDeleted)
    }

    override fun getMinCustomerInfo(): Flow<Resource<MinCustomerInfo>> = channelFlow {
        val accessToken = dataStoreManager.getAccessToken().first()

        val minCustomerInfoResource =
            queryGenerator.generateGetMinCustomerInfoQuery(accessToken)
                .enqueue1()
                .mapResource(mapper::mapToMinCustomerInfo)

        dataStoreManager.getCurrency().collect { currency ->
            send(minCustomerInfoResource.mapResource { it.copy(currency = currency) })
        }
    }

    override suspend fun signOut() {
        dataStoreManager.clearAccessToken()
    }

    override suspend fun getAddresses(): Resource<List<MyAccountMinAddress>> {
        val accessToken = dataStoreManager.getAccessToken().first()
        return queryGenerator.generateAddressQuery(accessToken)
            .enqueue1()
            .mapResource(mapper::mapToAddresses)
    }

    private fun Storefront.QueryRootQuery.enqueue() =
        graphClient.enqueue(this).map { result ->
            when (result) {
                is GraphCallResult.Success ->
                    Resource.Success(result.response)

                is GraphCallResult.Failure ->
                    Resource.Error(mapper.map(result.error))
            }
        }.applyDispatcher()

    private fun Storefront.MutationQuery.enqueue() =
        graphClient.enqueue(this).map { result ->
            when (result) {
                is GraphCallResult.Success ->
                    Resource.Success(result.response)

                is GraphCallResult.Failure ->
                    Resource.Error(mapper.map(result.error))
            }
        }.applyDispatcher()

    private suspend fun Storefront.QueryRootQuery.enqueue1() =
        graphClient.enqueue1(this).run {
            when (this) {
                is GraphCallResult.Success ->
                    Resource.Success(response)

                is GraphCallResult.Failure ->
                    Resource.Error(mapper.map(error))
            }
        }

    private suspend fun Storefront.MutationQuery.enqueue1() =
        graphClient.enqueue1(this).run {
            when (this) {
                is GraphCallResult.Success ->
                    Resource.Success(response)

                is GraphCallResult.Failure ->
                    Resource.Error(mapper.map(error))
            }
        }

    private fun <T> Flow<T>.applyDispatcher() = this.flowOn(defaultDispatcher)
    private fun <I, O> Resource<I>.mapResource(
        transform: (I) -> O
    ): Resource<O> {
        return run {
            when (this) {
                is Resource.Error -> this
                is Resource.Success -> Resource.Success(transform(data))
            }
        }
    }
}