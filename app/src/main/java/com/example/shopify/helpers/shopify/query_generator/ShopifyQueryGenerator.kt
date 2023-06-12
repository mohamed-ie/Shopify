package com.example.shopify.helpers.shopify.query_generator

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.MailingAddressInput
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
    fun generateCreateAddress(
        accessToken: String,
        address: MailingAddressInput
    ): Storefront.MutationQuery

    fun generateDeleteAddressQuery(addressId: ID, accessToken: String): Storefront.MutationQuery
    fun generateGetMinCustomerInfoQuery(accessToken: String): Storefront.QueryRootQuery
    fun generateAddressesQuery(accessToken: String): Storefront.QueryRootQuery
    fun generateCreateCustomerCartQuery(
        accessToken: String,
        productVariantId: ID,
        quantity: Int
    ): Storefront.MutationQuery

    fun generateAddCartLineQuery(
        cartId: ID,
        productVariantId: ID,
        quantity: Int
    ): Storefront.MutationQuery

    fun generateGetCartQuery(cartId: ID): Storefront.QueryRootQuery
    fun generateRemoveCartLineQuery(cartId: ID, linesId: List<ID>): Storefront.MutationQuery
    fun generateChangeCartLineQuantityQuery(
        cartId: ID,
        merchandiseId: ID,
        quantity: Int
    ): Storefront.MutationQuery

    fun generateApplyCouponQuery(cartId: ID, coupon: String): Storefront.MutationQuery
    fun generateUpdateCartAddress(accessToken: String,cartId: ID, addressId: ID): Storefront.MutationQuery
}