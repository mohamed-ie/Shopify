package com.example.shopify.helpers.shopify.mapper

import com.example.shopify.DraftOrderQuery
import com.example.shopify.DraftOrderUpdateMutation
import com.example.shopify.helpers.UIError
import com.example.shopify.model.Pageable
import com.example.shopify.model.auth.signin.SignInUserInfo
import com.example.shopify.model.auth.signin.SignInUserInfoResult
import com.example.shopify.model.auth.signup.SignUpUserResponseInfo
import com.example.shopify.model.cart.cart.Cart
import com.example.shopify.model.cart.order.Order
import com.example.shopify.model.home.Brand
import com.example.shopify.model.my_account.MinCustomerInfo
import com.example.shopify.model.product_details.Price
import com.example.shopify.model.product_details.Product
import com.example.shopify.ui.bottom_bar.home.product.model.BrandProduct
import com.shopify.buy3.GraphError
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.MailingAddress
import com.shopify.graphql.support.ID

interface ShopifyMapper {
    fun map(response: GraphResponse<Storefront.Mutation>): SignUpUserResponseInfo
    fun mapToBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<Brand>
    fun mapToSignInResponse(
        response: GraphResponse<Storefront.Mutation>,
        signInUserInfo: SignInUserInfo
    ): SignInUserInfoResult

    fun map(error: GraphError): UIError
    fun isAddressSaved(response: GraphResponse<Storefront.Mutation>): String?
    fun isAddressDeleted(response: GraphResponse<Storefront.Mutation>): Boolean
    fun mapToMinCustomerInfo(graphResponse: GraphResponse<Storefront.QueryRoot>): MinCustomerInfo
    fun mapToProduct(response: GraphResponse<Storefront.QueryRoot>): Product
    fun mapToProductsByBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<BrandProduct>
    fun mapToOrderResponse(
        response: GraphResponse<Storefront.QueryRoot>,
        currency: String,
        rate: Float
    ): List<Order>

    fun mapToCheckoutId(result: GraphResponse<Storefront.Mutation>): ID?
    fun mapToProductsCategoryResponse(response: GraphResponse<Storefront.QueryRoot>): List<BrandProduct>
    fun mapToProductsTypeResponse(response: GraphResponse<Storefront.QueryRoot>): List<String>
    fun mapToProductsTagsResponse(response: GraphResponse<Storefront.QueryRoot>): List<String>
    fun mapToAddresses(response: GraphResponse<Storefront.QueryRoot>): List<MailingAddress>
    fun mapToCartId(response: GraphResponse<Storefront.Mutation>): Pair<String?, String?>?
    fun mapToAddCartLine(response: GraphResponse<Storefront.Mutation>): String?
    fun mapMutationToCart(data: DraftOrderUpdateMutation.Data, currency: String, rate: Float): Cart?
    fun mapToUpdateCartAddress(response: GraphResponse<Storefront.Mutation>): String?
    fun mapToProductsByQueryResponse(response: GraphResponse<Storefront.QueryRoot>): Pageable<List<BrandProduct>>?
    fun mapPriceToLivePrice(liveCurrencyCode: String, liveAmount: Float, price: Price): Price
    fun mapPriceV2ToLivePrice(
        liveCurrencyCode: String,
        liveAmount: Float,
        price: Storefront.MoneyV2
    ): Storefront.MoneyV2

    fun mapQueryToCart(data: DraftOrderQuery.Data, currency: String, rate: Float): Cart?
    fun mapToUpdateCustomerInfo(response: GraphResponse<Storefront.Mutation>): String?
}