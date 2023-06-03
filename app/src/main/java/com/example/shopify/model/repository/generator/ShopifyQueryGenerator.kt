package com.example.shopify.model.repository.generator

import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.shopify.buy3.Storefront

interface ShopifyQueryGenerator {
    fun generateSingUpQuery(userInfo: SignUpUserInfo): Storefront.MutationQuery
    fun generateSingInQuery(userInfo: SignInUserInfo): Storefront.MutationQuery
    fun generateBrandQuery(): Storefront.QueryRootQuery?
    fun generateProductDetailsQuery(id: String): Storefront.QueryRootQuery
}