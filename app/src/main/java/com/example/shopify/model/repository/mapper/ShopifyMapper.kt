package com.example.shopify.model.repository.mapper

import com.example.shopify.ui.screen.auth.login.model.SignInUserResponseInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront

interface ShopifyMapper {
    fun map(response: GraphResponse<Storefront.Mutation>): SignUpUserResponseInfo
    fun mapToSignInResponse(response: GraphResponse<Storefront.Mutation>): SignInUserResponseInfo
}