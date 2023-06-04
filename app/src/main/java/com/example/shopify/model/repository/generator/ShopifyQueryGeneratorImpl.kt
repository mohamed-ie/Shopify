package com.example.shopify.model.repository.generator

import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.shopify.buy3.Storefront
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

    override fun generateProductDetailsQuery(id: String): Storefront.QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.node(ID("gid://shopify/Product/$id")) { nodeQuery ->
                nodeQuery.onProduct { productQuery ->
                    productQuery
                        .title()
                        .vendor()
                        .productType()
                        .tags()
                        .description()
                        .createdAt()
                        .isGiftCard
                        .onlineStoreUrl()
                        .requiresSellingPlan()
                        .totalInventory()
                        .priceRange { productPriceRangeQuery ->
                            productPriceRangeQuery.minVariantPrice { moneyV2Query ->
                                moneyV2Query
                                    .amount()
                                    .currencyCode()
                            }
                            productPriceRangeQuery.maxVariantPrice { moneyV2Query ->
                                moneyV2Query
                                    .amount()
                                    .currencyCode()
                            }
                        }
                        .images({ imageArguments -> imageArguments.first(5) }) { imageConnectionQuery ->
                            imageConnectionQuery.nodes { imageQuery ->
                                imageQuery.url()
                            }
                        }
                        .variants({ variantsArguments->  variantsArguments.first(5)}) { productVariantConnectionQuery ->
                            productVariantConnectionQuery.edges { productVariantEdgeQuery ->
                                productVariantEdgeQuery.node { productVariantQuery ->
                                    productVariantQuery.title()
                                        .image { imageQuery -> imageQuery.url() }
                                        .price { moneyV2Query -> moneyV2Query.amount() }
                                }
                            }
                        }
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