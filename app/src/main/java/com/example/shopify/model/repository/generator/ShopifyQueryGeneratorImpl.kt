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

    //8312397005107
    override fun generateProductDetailsQuery(id:String) : Storefront.QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.node(ID(id)){ nodeQuery ->
                nodeQuery.id()
                nodeQuery.onProduct {productQuery ->
                    productQuery
                        .title()
                        .description()
                        .productType()
                        .createdAt()
                        .vendor()
                        .isGiftCard
                        .availableForSale()
                        .tags()
                        .onlineStoreUrl()
                        .requiresSellingPlan()
                        .media {mediaConnectionQuery ->
                            mediaConnectionQuery.nodes {mediaQuery ->
                                mediaQuery
                                    .mediaContentType()
                            }
                        }
                        .images {imageConnectionQuery ->
                            imageConnectionQuery.nodes {imageQuery ->
                                imageQuery
                                    .id()
                                    .url()
                            }
                        }
                        .variants {variantConnectionQuery ->
                            variantConnectionQuery.nodes {productVariantQuery ->
                                productVariantQuery
                                    .title()
                                    .price {moneyQuery ->
                                        moneyQuery.amount()
                                    }
                            }
                        }
                        .priceRange {productPriceRangeQuery ->
                            productPriceRangeQuery
                                .maxVariantPrice {moneyQuery ->
                                    moneyQuery.amount()
                                }
                                .minVariantPrice { moneyQuery ->
                                    moneyQuery.amount()
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