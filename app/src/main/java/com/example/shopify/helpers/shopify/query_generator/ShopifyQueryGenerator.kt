package com.example.shopify.helpers.shopify.query_generator

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

interface ShopifyQueryGenerator {
    fun generateSingUpQuery(userInfo: SignUpUserInfo): Storefront.MutationQuery
    fun generateSingInQuery(userInfo: SignInUserInfo): Storefront.MutationQuery
    fun generateBrandQuery(): Storefront.QueryRootQuery?
    fun generateServerUrlQuery(): Storefront.QueryRootQuery
    fun generatePaymentCompletionAvailabilityQuery(
        checkoutId: ID,
        input: Storefront.CreditCardPaymentInputV2
    ): Storefront.MutationQuery
    fun generateUpdateCheckoutReadyQuery(paymentId: ID): Storefront.QueryRootQuery
    fun generateProductDetailsQuery(id: String): Storefront.QueryRootQuery
    fun generateProductByBrandQuery(brandName: String): Storefront.QueryRootQuery

}