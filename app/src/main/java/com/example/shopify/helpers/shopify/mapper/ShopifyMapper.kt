package com.example.shopify.helpers.shopify.mapper

import com.example.shopify.feature.address.addresses.model.MyAccountMinAddress
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment.ShopifyCreditCardPaymentStrategy
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.helpers.UIError
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphError
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

interface ShopifyMapper {
    fun map(response: GraphResponse<Storefront.Mutation>): SignUpUserResponseInfo
    fun mapToBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<Brand>
    fun mapToSignInResponse(
        response: GraphResponse<Storefront.Mutation>,
        signInUserInfo: SignInUserInfo
    ): SignInUserInfoResult

    fun map(error: GraphError): UIError
    fun mapToPaymentCompletionAvailability(result: GraphResponse<Storefront.Mutation>): ShopifyCreditCardPaymentStrategy.PaymentCompletionAvailability
    fun mapToPaymentResult(result: GraphCallResult.Success<Storefront.QueryRoot>): ShopifyCreditCardPaymentStrategy.PaymentResult
    fun isAddressSaved(response: GraphResponse<Storefront.Mutation>): Boolean
    fun isAddressDeleted(response: GraphResponse<Storefront.Mutation>): Boolean
    fun mapToMinCustomerInfo(graphResponse: GraphResponse<Storefront.QueryRoot>, ): MinCustomerInfo
    fun mapToProduct(response: GraphResponse<Storefront.QueryRoot>): Product
    fun mapToProductsByBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<BrandProduct>
    fun mapToOrderResponse(response: GraphResponse<Storefront.QueryRoot>): List<Order>
    fun mapToCheckoutId(result: GraphResponse<Storefront.Mutation>): ID?
    fun mapToProductsCategoryResponse(response: GraphResponse<Storefront.QueryRoot>): List<BrandProduct>
    fun mapToProductsTypeResponse(response: GraphResponse<Storefront.QueryRoot>): List<String>
    fun mapToProductsTagsResponse(response: GraphResponse<Storefront.QueryRoot>): List<String>
    fun mapToAddresses(response: GraphResponse<Storefront.QueryRoot>): List<MyAccountMinAddress>
    fun mapToCartId(response: GraphResponse<Storefront.Mutation>): Pair<String?, String?>?
    fun mapToAddCartLine(response: GraphResponse<Storefront.Mutation>): String?
    fun mapToCart(graphResponse: GraphResponse<Storefront.QueryRoot>): Cart?
    fun mapToRemoveCartLines(response: GraphResponse<Storefront.Mutation>): Cart?
    fun mapToChangeCartLineQuantity(response: GraphResponse<Storefront.Mutation>): Cart?
    fun mapToApplyCouponToCart(response: GraphResponse<Storefront.Mutation>): Cart?
    fun mapToUpdateCartAddress(response: GraphResponse<Storefront.Mutation>): String?

}