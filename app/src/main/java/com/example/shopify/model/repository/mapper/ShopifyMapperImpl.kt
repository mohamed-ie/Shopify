package com.example.shopify.model.repository.mapper

import com.example.shopify.helpers.UIError
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfoResult
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.example.shopify.ui.screen.home.model.Brand
import com.shopify.buy3.GraphError
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront
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
}