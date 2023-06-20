package com.example.shopify.feature.navigation_bar.model.repository.shopify

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Optional
import com.example.shopify.DraftOrderCompleteMutation
import com.example.shopify.DraftOrderCreateMutation
import com.example.shopify.DraftOrderDeleteMutation
import com.example.shopify.DraftOrderInvoiceSendMutation
import com.example.shopify.DraftOrderLineItemsQuery
import com.example.shopify.DraftOrderQuery
import com.example.shopify.DraftOrderUpdateMutation
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.common.model.Pageable
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.model.local.ShopifyDataStoreManager
import com.example.shopify.feature.navigation_bar.model.remote.fireStore.FireStoreManager
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.UIError
import com.example.shopify.helpers.getOrNull
import com.example.shopify.helpers.mapResource
import com.example.shopify.helpers.mapSuspendResource
import com.example.shopify.helpers.shopify.mapper.ShopifyMapper
import com.example.shopify.helpers.shopify.query_generator.ShopifyQueryGenerator
import com.example.shopify.type.DraftOrderDeleteInput
import com.example.shopify.type.DraftOrderInput
import com.example.shopify.type.DraftOrderLineItemInput
import com.example.shopify.type.MailingAddressInput
import com.example.shopify.utils.Constants
import com.example.shopify.utils.mapResource
import com.example.shopify.utils.mapSuspendResource
import com.example.shopify.utils.shopify.enqueue
import com.example.shopify.utils.shopify.enqueue1
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import javax.inject.Inject


class ShopifyRepositoryImpl @Inject constructor(
    private val graphClient: GraphClient,
    private val queryGenerator: ShopifyQueryGenerator,
    private val mapper: ShopifyMapper,
    private val dataStoreManager: ShopifyDataStoreManager,
    private val fireStoreManager: FireStoreManager,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val apolloClient: ApolloClient
) : ShopifyRepository {
    private val adminManager = AdminManager()
    private val storeFrontManager = StoreFrontManager()

    override fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>> {
        return queryGenerator.generateSingUpQuery(userInfo)
            .enqueue()
            .mapResource(mapper::map)
    }

    override fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserInfoResult>> {
        return queryGenerator.generateSingInQuery(userInfo)
            .enqueue()
            .mapResource { response -> mapper.mapToSignInResponse(response, userInfo) }
            .mapSuspendResource { data ->
                if (data.accessToken.isNotBlank()) {
                    dataStoreManager.setEmail(data.email)
                    updateCustomerId(data.accessToken)
                }
                joinAll()
                data
            }

    }

    private suspend fun updateCustomerId(accessToken: String) {
        queryGenerator.generateGetCustomerId(accessToken)
            .enqueue1()
            .mapSuspendResource { data ->
                data.data
                    ?.customer
                    ?.id
                    ?.toString()
                    ?.let { dataStoreManager.setCustomerId(it) }
            }
    }

    override suspend fun saveUserInfo(userResponseInfo: SignInUserInfoResult) =
        dataStoreManager.saveUserInfo(userResponseInfo)


    override fun getUserInfo(): Flow<SignInUserInfoResult> =
        dataStoreManager.getUserInfo()


    override fun isLoggedIn(): Flow<Boolean> =
        dataStoreManager.getAccessToken()
            .map { it.isNotBlank() }
            .flowOn(defaultDispatcher)

    override fun getBrands(): Flow<Resource<List<Brand>>> {
        val query = queryGenerator.generateBrandQuery()
        return query.enqueue().mapResource(mapper::mapToBrandResponse)
    }

    override suspend fun getCart(): Resource<Cart?> {
        val email = dataStoreManager.getEmail().first()

        val cartId = fireStoreManager.getCurrentCartId(email)
            .getOrNull() ?: return Resource.Success(Cart())
        return adminManager.getDraftOrder(cartId)
    }

    override suspend fun getProductsByBrandName(brandName: String): Resource<List<BrandProduct>> {
        val query = queryGenerator.generateProductByBrandQuery(brandName)
        val email = dataStoreManager.getEmail().first()
        val wishList = fireStoreManager.getWishList(email)
        return query.enqueue1()
            .mapResource(mapper::mapToProductsByBrandResponse)
            .mapSuspendResource { brandProducts ->
                brandProducts.map { brandProduct ->
                    brandProduct.copy(
                        isFavourite = wishList.getOrNull()?.contains(brandProduct.id) ?: false,
                        price = mapper.mapPriceV2ToLivePrice(
                            dataStoreManager.getCurrency().first(),
                            dataStoreManager.getCurrencyAmountPerOnePound().first(),
                            brandProduct.price
                        )
                    )
                }
            }
    }

    override suspend fun getProductsByQuery(
        productQueryType: Constants.ProductQueryType,
        queryContent: String,
    ): Resource<Pageable<List<BrandProduct>>?> {
        val query = queryGenerator.generateProductsByQuery(productQueryType, queryContent)
        return query.enqueue1()
            .mapSuspendResource {
                mapper.mapToProductsByQueryResponse(it)?.let { page ->
                    page.copy(data = page.data.map { brandProduct ->
                        brandProduct.copy(
                            isFavourite = isProductWishList(brandProduct.id),
                            price = mapper.mapPriceV2ToLivePrice(
                                dataStoreManager.getCurrency().first(),
                                dataStoreManager.getCurrencyAmountPerOnePound().first(),
                                brandProduct.price
                            )
                        )
                    })
                }
            }
    }


    override fun getProductDetailsByID(id: String): Flow<Resource<Product>> =
        queryGenerator.generateProductDetailsQuery(id)
            .enqueue()
            .mapSuspendResource {
                mapper.mapToProduct(it).let { product ->
                    product.copy(
                        isFavourite = isProductWishList(product.id),
                        price = mapper.mapPriceToLivePrice(
                            dataStoreManager.getCurrency().first(),
                            dataStoreManager.getCurrencyAmountPerOnePound().first(),
                            product.price
                        )
                    )

                }
            }

    override suspend fun getProductReviewById(productId: ID, reviewsCount: Int?) =
        fireStoreManager.getReviewsByProductId(productId, reviewsCount).getOrNull() ?: emptyList()

    override suspend fun updateCurrency(currency: String) {
        dataStoreManager.setCurrency(currency)
    }

    override suspend fun setProductReview(productId: ID, review: Review) =
        fireStoreManager.setProductReviewByProductId(productId, review)


    override suspend fun saveAddress(address: Storefront.MailingAddressInput): Resource<Boolean> {
        val accessToken = dataStoreManager.getAccessToken().first()

        return queryGenerator.generateCreateAddress(accessToken, address)
            .enqueue1()
            .mapResource(mapper::isAddressSaved)
    }

    override suspend fun deleteAddress(addressId: ID): Resource<Boolean> {
        val accessToken = dataStoreManager.getAccessToken().first()

        return queryGenerator.generateDeleteAddressQuery(addressId, accessToken)
            .enqueue1()
            .mapResource(mapper::isAddressDeleted)
    }

    override suspend fun getMinCustomerInfo(): Resource<MinCustomerInfo> {
        val accessToken = dataStoreManager.getAccessToken().first()
        return storeFrontManager.getMinCustomerInfo(accessToken)
    }

    private inner class StoreFrontManager() {

        suspend fun getMinCustomerInfo(accessToken: String): Resource<MinCustomerInfo> {
            return queryGenerator.generateGetMinCustomerInfoQuery(accessToken)
                .enqueue1()
                .mapResource(mapper::mapToMinCustomerInfo)
        }
    }

    override suspend fun signOut() {
        dataStoreManager.clear()
    }

    override suspend fun getAddresses(): Resource<List<Storefront.MailingAddress>> {
        val accessToken = dataStoreManager.getAccessToken().first()
        return queryGenerator.generateAddressesQuery(accessToken)
            .enqueue1()
            .mapResource(mapper::mapToAddresses)
    }

    //this resource returns error message if failed
    override suspend fun addToCart(productVariantId: String, quantity: Int): Resource<String?> {
        var customerId = dataStoreManager.getCustomerId().first()
        val email = dataStoreManager.getEmail().first()
        if (customerId.isBlank())
            updateCustomerId(dataStoreManager.getAccessToken().first())
        joinAll()
        customerId = dataStoreManager.getCustomerId().first()
        return getCartId(email).getOrNull()?.let { cartId ->
            //add line to cart if already exist
            adminManager.addToDraftOrder(cartId, productVariantId, quantity)
        } ?:
        //otherwise create cart with new line
        adminManager.createDraftOrder(customerId, productVariantId, quantity)
            .mapSuspendResource { pair ->
                //also save cart id
                pair?.first?.let { fireStoreManager.setCurrentCartId(email, it) }
                joinAll()
                //return server error message
                pair?.second
            }
    }


    override suspend fun removeCartLines(productVariantId: String): Resource<Cart?> {
        val email = dataStoreManager.getEmail().first()
        return when (val resource = getCartId(email)) {
            is Resource.Error -> resource
            is Resource.Success -> {
                val cartId = resource.data ?: return Resource.Success(Cart())
                adminManager.removeCartLine(cartId, productVariantId)
                    .mapSuspendResource {
                        it?.lines?.isEmpty()?.let { fireStoreManager.clearDraftOrderId(email) }
                        joinAll()
                        it
                    }
            }
        }
    }

    override suspend fun completeOrder(paymentPending: Boolean): Resource<String?> {
        val email = dataStoreManager.getEmail().first()
        val cartId = getCartId(email).getOrNull() ?: return Resource.Error(UIError.Unexpected)
        return adminManager.completeDraftOrder(cartId, paymentPending)
            .mapSuspendResource {
                fireStoreManager.clearDraftOrderId(email)
                joinAll()
                it
            }
    }

    override suspend fun sendCompletePayment(): Resource<Pair<String?, String?>?> {
        val email = dataStoreManager.getEmail().first()
        val cartId = getCartId(email).getOrNull() ?: return Resource.Error(UIError.Unexpected)
        return adminManager.sendInvoice(cartId)
    }

    override suspend fun changeCartLineQuantity(
        merchandiseId: String,
        quantity: Int
    ): Resource<Cart?> {
        val email = dataStoreManager.getEmail().first()
        val cartId = getCartId(email).getOrNull() ?: return Resource.Error(UIError.Unexpected)

        return adminManager.changeDraftOrderLineQuantity(cartId, merchandiseId, quantity)
    }

    override suspend fun applyCouponToCart(coupon: String): Resource<Cart?> {
        val email = dataStoreManager.getEmail().first()
//        val cartId = getCartId(email) ?:
        return Resource.Error(UIError.Unexpected)

//        return queryGenerator.generateApplyCouponQuery(ID(cartId), coupon)
//            .enqueue1()
//            .mapResource(mapper::mapToApplyCouponToCart)
    }

    override suspend fun changePassword(password: String): Resource<String?> {
        val accessToken = dataStoreManager.getAccessToken().first()
        val input = Storefront.CustomerUpdateInput()
            .setPassword(password)
        return queryGenerator.generateUpdateCustomerQuery(accessToken, input)
            .enqueue1()
            .mapResource(mapper::mapToUpdateCustomerInfo)
            .mapSuspendResource {
                if (it == null) signOut()
                joinAll()
                it
            }
    }

    override suspend fun changePhoneNumber(phone: String): Resource<String?> {
        val accessToken = dataStoreManager.getAccessToken().first()
        val input = Storefront.CustomerUpdateInput()
            .setPhone(phone)
        return queryGenerator.generateUpdateCustomerQuery(accessToken, input)
            .enqueue1()
            .mapResource(mapper::mapToUpdateCustomerInfo)
    }

    override suspend fun updateCartShippingAddress(address: Storefront.MailingAddress): Resource<String?> {
        val email = dataStoreManager.getEmail().first()
        val cartId = getCartId(email).getOrNull() ?: return Resource.Error(UIError.Unexpected)

        return adminManager.updateShippingAddress(cartId, address)
    }

    override suspend fun changeName(firstName: String, lastName: String): Resource<String?> {
        val accessToken = dataStoreManager.getAccessToken().first()
        val input = Storefront.CustomerUpdateInput()
            .setFirstName(firstName)
            .setLastName(lastName)

        return queryGenerator.generateUpdateCustomerQuery(accessToken, input)
            .enqueue1()
            .mapResource(mapper::mapToUpdateCustomerInfo)
    }

    override suspend fun createUserEmail(email: String): Resource<Unit> =
        fireStoreManager.createUserEmail(email)

    private suspend fun getCartId(email: String) =
        fireStoreManager.getCurrentCartId(email)


    override suspend fun addProductWishListById(productId: ID) =
        fireStoreManager.updateWishList(dataStoreManager.getEmail().first(), productId)

    override suspend fun removeProductWishListById(productId: ID) =
        fireStoreManager.removeAWishListProduct(dataStoreManager.getEmail().first(), productId)

    override suspend fun getOrders(): Flow<Resource<List<Order>>> {
        val accessToken = dataStoreManager.getAccessToken().first()
        return queryGenerator.generateUserOrdersQuery(accessToken).enqueue()
            .mapResource(mapper::mapToOrderResponse)
    }

    private suspend fun getWishList(customerId: String): List<ID> =
        fireStoreManager.getWishList(customerId).getOrNull() ?: emptyList()


    override fun getShopifyProductsByWishListIDs() = flow {
        getWishList(dataStoreManager.getEmail().first())
            .also { productList ->
                productList.ifEmpty {
                    emit(Resource.Success(null))
                }
            }.forEach { id ->
                emit(getProductDetailsByID(id.toString()).first())
            }
    }

    private suspend fun isProductWishList(productId: ID): Boolean =
        getWishList(dataStoreManager.getEmail().first()).find { id ->
            id == productId
        } != null

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

    override suspend fun getCheckOutId(cart: Cart): Flow<Resource<ID?>> {
        val email = dataStoreManager.getEmail().first()
        return queryGenerator.checkoutCreate(cart, email).enqueue()
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

    override suspend fun getProductsTag(): Resource<List<String>> {
        return queryGenerator.generateProductTagsQuery().enqueue1()
            .mapResource(mapper::mapToProductsTagsResponse)

    }

    override suspend fun getProductsType(): Resource<List<String>> {
        return queryGenerator.generateProductTypesQuery().enqueue1()
            .mapResource(mapper::mapToProductsTypeResponse)
    }


    private inner class AdminManager() {

        // return pair of draftOrderId and totalPrice
        suspend fun createDraftOrder(
            customerId: String,
            variantId: String,
            quantity: Int
        ): Resource<Pair<String, String>?> {
            val newLine = DraftOrderLineItemInput(variantId = variantId.present(), quantity = quantity)
            val lines = listOf(newLine)
            val input = DraftOrderInput(
                customerId = customerId.present(),
                lineItems = lines.present()
            )
            val createInput = DraftOrderCreateMutation(input = input)

            return apolloClient.mutation(createInput)
                .executeCatching()
                .mapResource {
                    it.draftOrderCreate
                        ?.draftOrder
                        ?.run {
                            val total = "${this.currencyCode} ${this.totalPrice}"
                            Pair(id, total)
                        }
                }
        }

        suspend fun addToDraftOrder(
            draftOrderId: String,
            variantId: String,
            quantity: Int
        ): Resource<String?> {
            val lineItems = getDraftOrderLineItems(draftOrderId)
                ?: return Resource.Error(UIError.Unexpected)

            lineItems.apply {
                val newLine =
                    DraftOrderLineItemInput(variantId = variantId.present(), quantity = quantity)
                if (none { it.variantId.getOrNull() == variantId })
                    add(newLine)
                else return Resource.Success(null)
            }

            val input = DraftOrderInput(lineItems = lineItems.present())
            val updateInput = DraftOrderUpdateMutation(draftOrderId, input, Optional.Absent)

            return updateDraftOrder(updateInput)
                .mapResource { it?.totalPrice }
        }

        //return error message if failed
        suspend fun deleteDraftOrder(draftOrderId: String): Resource<String?> =
            apolloClient.mutation(DraftOrderDeleteMutation(DraftOrderDeleteInput(draftOrderId)))
                .executeCatching()
                .mapResource { it.draftOrderDelete?.userErrors?.getOrNull(0)?.message }


        /*
        since draft order must contain at least 1 item
        delete draft order if one line item exist
        otherwise update draft order remove line item
         */
        suspend fun removeCartLine(draftOrderId: String, variantId: String): Resource<Cart?> {
            val lineItems = getDraftOrderLineItems(draftOrderId)
                ?: return Resource.Error(UIError.Unexpected)

            if (lineItems.size == 1)
                return deleteDraftOrder(draftOrderId)
                    .mapResource { Cart(error = it) }
            else
                lineItems.apply { removeIf { it.variantId.getOrNull() == variantId } }

            val input = DraftOrderInput(lineItems = lineItems.present())
            val updateInput = DraftOrderUpdateMutation(draftOrderId, input, Optional.Absent)

            return updateDraftOrder(updateInput)
        }

        private suspend fun updateDraftOrder(input: DraftOrderUpdateMutation): Resource<Cart?> {
            return apolloClient.mutation(input)
                .executeCatching()
                .mapResource { mapper.mapMutationToCart(it) }
        }

        suspend fun changeDraftOrderLineQuantity(
            draftOrderId: String,
            variantId: String,
            quantity: Int
        ): Resource<Cart?> {
            val lineItems = getDraftOrderLineItems(draftOrderId)
                ?: return Resource.Error(UIError.Unexpected)

            lineItems.apply {
                removeIf { it.variantId.getOrNull() == variantId }
                val newItem = DraftOrderLineItemInput(
                    variantId = variantId.present(),
                    quantity = quantity
                )
                add(newItem)
            }

            val input = DraftOrderInput(lineItems = lineItems.present())
            val updateInput = DraftOrderUpdateMutation(draftOrderId, input = input)

            return updateDraftOrder(updateInput)
        }

        suspend fun completeDraftOrder(
            draftOrderId: String,
            paymentPending: Boolean
        ): Resource<String?> {
            return apolloClient.mutation(DraftOrderCompleteMutation(draftOrderId, paymentPending))
                .executeCatching()
                .mapResource { it.draftOrderComplete?.userErrors?.getOrNull(0)?.message }
        }

        suspend fun sendInvoice(cartId: String): Resource<Pair<String?, String?>?> {
            return apolloClient.mutation(DraftOrderInvoiceSendMutation(cartId))
                .executeCatching()
                .mapResource {
                    it.draftOrderInvoiceSend?.run {
                        Pair(
                            draftOrder?.invoiceUrl.toString(),
                            userErrors.getOrNull(0)?.message
                        )
                    }
                }
        }

        suspend fun updateShippingAddress(
            draftOrderId: String,
            address: Storefront.MailingAddress
        ): Resource<String?> {
            val addressInput = MailingAddressInput(
                address1 = address.address1.present(),
                address2 = address.address2.present(),
                city = address.city.present(),
                company = address.company.present(),
                country = address.country.present(),
                firstName = address.firstName.present(),
                lastName = address.lastName.present(),
                phone = address.phone.present(),
                zip = address.zip.present(),
                province = address.province.present()
            )
            val input = DraftOrderInput(shippingAddress = addressInput.present())
            val updateInput = DraftOrderUpdateMutation(draftOrderId, input)
            return updateDraftOrder(updateInput)
                .mapResource { it?.error }
        }

        private suspend fun getDraftOrderLineItems(
            draftOrderId: String
        ): MutableList<DraftOrderLineItemInput>? {
            return apolloClient.query(DraftOrderLineItemsQuery(draftOrderId, Optional.Absent))
                .executeCatching()
                .getOrNull()
                ?.draftOrder
                ?.lineItems
                ?.nodes
                ?.map {
                    DraftOrderLineItemInput(
                        quantity = it.quantity,
                        variantId = it.variant?.id.present()
                    )
                }
                ?.toMutableList()
        }

        suspend fun getDraftOrder(cartId: String): Resource<Cart?> {
            return apolloClient.query(DraftOrderQuery(cartId, Optional.Absent))
                .executeCatching()
                .mapResource { mapper.mapQueryToCart(it) }
        }

        private suspend fun <D : Operation.Data> ApolloCall<D>.executeCatching(): Resource<D> =
            try {
                val response = execute()
                if (response.hasErrors())
                    Resource.Error(UIError.Unexpected)
                else
                    Resource.Success(response.dataAssertNoErrors)
            } catch (e: Exception) {
                Resource.Error(UIError.Unexpected)
            }


        @JvmName("present")
        private fun <T : Any> T?.present(): Optional<T?> = Optional.present(this)

        @JvmName("null_present")
        private fun <T : Any> T.present(): Optional<T> = Optional.present(this)


    }
}
