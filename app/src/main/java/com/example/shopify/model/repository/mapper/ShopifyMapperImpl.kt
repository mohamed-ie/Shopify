package com.example.shopify.model.repository.mapper

import com.example.shopify.ui.screen.auth.login.model.SignInUserResponseInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.example.shopify.ui.screen.home.model.Brand
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

    override fun mapToSignInResponse(response: GraphResponse<Storefront.Mutation>): SignInUserResponseInfo {
        val customerAccessToken = response.data?.customerAccessTokenCreate?.customerAccessToken
        return SignInUserResponseInfo(
            customerAccessToken?.accessToken ?: "",
            customerAccessToken?.expiresAt,
            response.data?.customerAccessTokenCreate?.customerUserErrors?.getOrNull(0)?.message
                ?: ""
        )
    }

    override fun mapToBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<Brand>? {
        return response.data?.collections?.edges?.drop(1)?.map {
            Brand(title = it.node.title, url = it.node.image?.url)
        }
    }
}