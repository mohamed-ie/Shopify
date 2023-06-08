package com.example.shopify.helpers.shopify.mapper

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment.ShopifyCreditCardPaymentStrategy
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.UIError
import com.google.firebase.firestore.DocumentSnapshot
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphError
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

interface ShopifyMapper {
    fun map(response: GraphResponse<Storefront.Mutation>): SignUpUserResponseInfo
    fun mapToBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<Brand>?
    fun mapToSignInResponse(
        response: GraphResponse<Storefront.Mutation>,
        signInUserInfo: SignInUserInfo
    ): SignInUserInfoResult

    fun map(error: GraphError): UIError
    fun mapToPaymentCompletionAvailability(result: GraphResponse<Storefront.Mutation>): ShopifyCreditCardPaymentStrategy.PaymentCompletionAvailability
    fun mapToPaymentResult(result: GraphCallResult.Success<Storefront.QueryRoot>): ShopifyCreditCardPaymentStrategy.PaymentResult
    fun mapToProduct(response: GraphResponse<Storefront.QueryRoot>): Product
    fun mapSnapShotDocumentToReview(snapshots: List<DocumentSnapshot>): List<Review>
    fun mapToProductsByBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<BrandProduct>
    fun mapToOrderResponse(response: GraphResponse<Storefront.QueryRoot>): List<Order>
    fun mapToCheckoutId(result: GraphResponse<Storefront.Mutation>): ID?

}