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
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.shopify.mapper.ShopifyMapper
import com.example.shopify.helpers.shopify.query_generator.ShopifyQueryGenerator
import com.example.shopify.utils.enqueue
import com.example.shopify.utils.mapResource
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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

    override fun getProductDetailsByID(id:String) : Flow<Resource<Product>>  =
        queryGenerator.generateProductDetailsQuery(id)
            .enqueue()
            .mapResource(mapper::mapToProduct)



    override fun isLoggedIn(): Flow<Boolean> =
        dataStoreManager.getAccessToken()
            .map { it != null }
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

    override suspend fun getProductReviewById(productId:String,reviewsCount:Int?) =
        withContext(defaultDispatcher){
            fireStoreManager.getReviewsByProductId(productId,reviewsCount).let {documentSnapshots ->
                mapper.mapSnapShotDocumentToReview(documentSnapshots.take(reviewsCount ?: documentSnapshots.count()))
            }
        }

    override suspend fun setProductReview(productId: String,review: Review) =
        fireStoreManager.setProductReviewByProductId(productId, review)


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

    private fun <T> Flow<T>.applyDispatcher() = this.flowOn(defaultDispatcher)

    override fun getCheckOutId(cart: Cart): Flow<Resource<ID?>> {
        return queryGenerator.checkoutCreate(cart).enqueue()
            .mapResource {
                mapper.mapToCheckoutId(it)
            }
    }

    override fun getProductsCategory(
        productType: String,
        productTag: String
    ): Flow<Resource<List<BrandProduct>>> {
        return queryGenerator.generateProductCategoryQuery(productType, productTag).enqueue()
            .mapResource(mapper::mapToProductsCategoryResponse)
    }

    override fun getProductsTag(): Flow<Resource<List<String>>> {
        return queryGenerator.generateProductTagsQuery().enqueue()
            .mapResource(mapper::mapToProductsTagsResponse)

    }

    override fun getProductsType(): Flow<Resource<List<String>>> {
        return queryGenerator.generateProductTypesQuery().enqueue()
            .mapResource(mapper::mapToProductsTypeResponse)
    }
}