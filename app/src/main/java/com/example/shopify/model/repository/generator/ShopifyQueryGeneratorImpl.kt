package com.example.shopify.model.repository.generator

import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.shopify.buy3.Storefront
import javax.inject.Inject

class ShopifyQueryGeneratorImpl @Inject constructor() :ShopifyQueryGenerator {
    override fun generateSingUpQuery(userInfo: SignUpUserInfo): Storefront.MutationQuery =
        Storefront.mutation { rootQuery ->
            rootQuery.customerCreate(createSingUpCustomerCreateInput(userInfo)) { payload ->
                payload.customer { customQuery ->
                    customQuery.firstName()
                        .lastName()
                        .email()
                        .phone()
                        .id()
                }.customerUserErrors { userErrorQuery ->
                    userErrorQuery.field().message()
                }
            }
        }

    override fun generateSingInQuery(userInfo: SignInUserInfo): Storefront.MutationQuery =
        Storefront.mutation { rootQuery ->
            rootQuery.customerAccessTokenCreate(createSingInCustomerCreateInput(userInfo)) { payload ->
                payload.customerAccessToken { customQuery ->
                    customQuery
                        .accessToken()
                        .expiresAt()
                }.customerUserErrors { userErrorQuery ->
                    userErrorQuery.field().message()
                }
            }
        }


    private fun createSingUpCustomerCreateInput(userInfo: SignUpUserInfo) =
        Storefront.CustomerCreateInput(userInfo.email, userInfo.password)
            .setFirstName(userInfo.firstName)
            .setLastName(userInfo.lastName)
            .setAcceptsMarketing(true)
            .setPhone(userInfo.phone)


    private fun createSingInCustomerCreateInput(signInUserInfo: SignInUserInfo) =
        Storefront.CustomerAccessTokenCreateInput(signInUserInfo.email, signInUserInfo.password)


}