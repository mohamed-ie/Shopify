package com.example.shopify.model.repository.mapper

import com.example.shopify.helpers.UIError
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment.ShopifyCreditCardPaymentStrategy
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.shopify.buy3.GraphCallResult
import com.example.shopify.ui.screen.Product.model.Product
import com.example.shopify.ui.screen.Product.model.Variants
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfoResult
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.example.shopify.ui.screen.home.model.Brand
import com.shopify.buy3.GraphError
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.ImageConnection
import javax.inject.Inject

class ShopifyMapperImpl @Inject constructor() : ShopifyMapper {

    override fun map(response: GraphResponse<Storefront.Mutation>): SignUpUserResponseInfo {
        val customer = response.data?.customerCreate?.customer
        return SignUpUserResponseInfo(
            customer?.firstName ?: "",
            customer?.lastName ?: "",
            customer?.email ?: "",
            customer?.phone ?: "",
            customer?.id.toString(),
            response.errors.getOrNull(0)?.message()
                ?: response.data?.customerCreate?.customerUserErrors?.getOrNull(0)?.message
        )
    }

    override fun mapToSignInResponse(
        response: GraphResponse<Storefront.Mutation>,
        signInUserInfo: SignInUserInfo
    ): SignInUserInfoResult {
        val customerAccessToken = response.data?.customerAccessTokenCreate?.customerAccessToken
        return SignInUserInfoResult(
            signInUserInfo.email,
            signInUserInfo.password,
            customerAccessToken?.accessToken ?: "",
            customerAccessToken?.expiresAt?.toDate()?.time.toString().takeIf { it != "null" },
            response.data?.customerAccessTokenCreate?.customerUserErrors?.getOrNull(0)?.message
                ?: ""
        )
    }

    override fun mapToProductsByBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<Product> {
        val res = response.data?.collections?.edges?.get(0)?.node?.products?.edges?.map {
            Product(
                id = it.node.id,
                title = it.node.title, description = it.node.description,
                images = mapToImageUrl(it.node.images), variants = mapToVariant(it.node.variants)
            )
        } ?: listOf()
        return res
    }

    private fun mapToImageUrl(response: ImageConnection): List<String> {
        return response.edges.map {
            it.node.url
        }
    }

    private fun mapToVariant(response: Storefront.ProductVariantConnection): Variants {
        return response.edges.firstOrNull()?.node.let {
            Variants(it!!.availableForSale, it.price)
        }
    }

    override fun mapToBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<Brand>? {
        return response.data?.collections?.edges?.drop(1)?.map {
            Brand(title = it.node.title, url = it.node.image?.url)
        }
    }

    override fun map(error: GraphError): UIError =
        when (error) {
            is GraphError.NetworkError -> UIError.NoInternetConnection
            is GraphError.HttpError -> UIError.RequestFailed
            is GraphError.CallCanceledError -> UIError.Canceled
            is GraphError.ParseError, is GraphError.Unknown -> UIError.Unexpected
        }

    override fun mapToPaymentCompletionAvailability(result: GraphResponse<Storefront.Mutation>): ShopifyCreditCardPaymentStrategy.PaymentCompletionAvailability {
        val checkout = result.data?.checkoutCompleteWithCreditCardV2
        val errors = checkout?.checkoutUserErrors
            ?.map { it.message }
            .takeIf { it != null } ?: listOf()
        val checkoutReady = checkout?.checkout?.ready ?: false
        val paymentReady = checkout?.payment?.ready ?: false
        val paymentId = checkout?.payment?.id?.toString()

        return ShopifyCreditCardPaymentStrategy.PaymentCompletionAvailability(
            errors = errors,
            checkoutReady = checkoutReady,
            paymentReady = paymentReady,
            paymentId = paymentId
        )
    }

    override fun mapToPaymentResult(result: GraphCallResult.Success<Storefront.QueryRoot>): ShopifyCreditCardPaymentStrategy.PaymentResult {
        val payment = result.response.data?.node as Storefront.Payment?
        val orderId = payment?.checkout
            ?.order
            ?.id
            ?.toString()

        return ShopifyCreditCardPaymentStrategy.PaymentResult(
            error = payment?.errorMessage,
            orderId = orderId,
            ready = payment?.ready ?: false,
            orderNumber = payment?.checkout?.order?.orderNumber,
            totalPrice = payment?.checkout?.totalPrice?.run { "$currencyCode $amount" }
        )
    }
}