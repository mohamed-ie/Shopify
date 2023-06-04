package com.example.shopify.model.repository.mapper

import com.example.shopify.helpers.UIError
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.login.model.SignInUserInfoResult
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.example.shopify.ui.screen.home.model.Brand
import com.example.shopify.ui.screen.productDetails.model.Product
import com.shopify.buy3.GraphError
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront

interface ShopifyMapper {
    fun map(response: GraphResponse<Storefront.Mutation>): SignUpUserResponseInfo
    fun mapToBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<Brand>?
    fun mapToSignInResponse(response: GraphResponse<Storefront.Mutation>,signInUserInfo: SignInUserInfo): SignInUserInfoResult
    fun map(error:GraphError): UIError
    fun mapToProduct(response: GraphResponse<Storefront.QueryRoot>):Product
}