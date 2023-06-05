package com.example.shopify.helpers.shopify.query_generator

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.CreditCardPaymentInputV2
import com.shopify.buy3.Storefront.MutationQuery
import com.shopify.graphql.support.ID
import javax.inject.Inject


class ShopifyQueryGeneratorImpl @Inject constructor() : ShopifyQueryGenerator {
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

    override fun generateBrandQuery(): Storefront.QueryRootQuery? =
        Storefront.query { rootQuery: Storefront.QueryRootQuery ->
            rootQuery.collections({ arg -> arg.first(10) }
            ) { collectionConnectionQuery ->
                collectionConnectionQuery.edges { collectionEdgeQuery ->
                    collectionEdgeQuery
                        .node { collectionQuery ->
                            collectionQuery
                                .title()
                                .image {
                                    it.url()
                                }
                        }
                }
            }
        }

    override fun generateServerUrlQuery(): Storefront.QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.shop { shopQuery ->
                shopQuery.paymentSettings { paymentSettings -> paymentSettings.cardVaultUrl() }
            }
        }

    override fun generatePaymentCompletionAvailabilityQuery(checkoutId: ID, input: CreditCardPaymentInputV2): MutationQuery =
        Storefront.mutation { mutationQuery: MutationQuery ->
            mutationQuery.checkoutCompleteWithCreditCardV2(checkoutId, input) { payloadQuery ->
                payloadQuery.payment { paymentQuery ->
                    paymentQuery.ready()
                        .errorMessage()
                }
                    .checkout { checkoutQuery -> checkoutQuery.ready() }
                    .checkoutUserErrors { userErrorQuery ->
                        userErrorQuery
                            .message()
                    }
            }
        }

    override fun generateUpdateCheckoutReadyQuery(paymentId: ID): Storefront.QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.node(paymentId) { nodeQuery ->
                nodeQuery.onPayment { paymentQuery ->
                    paymentQuery.checkout { checkoutQuery ->
                        checkoutQuery.order { orderQuery ->
                            orderQuery
                                .orderNumber()
                                .totalPrice {
                                    it.amount()
                                        .currencyCode()
                                }
                        }
                    }.errorMessage()
                        .ready()
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