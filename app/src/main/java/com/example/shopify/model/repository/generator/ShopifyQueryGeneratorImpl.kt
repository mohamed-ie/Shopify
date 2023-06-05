package com.example.shopify.model.repository.generator

import com.example.shopify.ui.screen.auth.login.model.SignInUserInfo
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserInfo
import com.shopify.buy3.Storefront
import javax.inject.Inject

class ShopifyQueryGeneratorImpl @Inject constructor() : ShopifyQueryGenerator {
    override fun generateSingUpQuery(userInfo: SignUpUserInfo): Storefront.MutationQuery =
        Storefront.mutation { rootQuery ->
            rootQuery.customerCreate(createSingUpCustomerCreateInput(userInfo)) { payload ->
                payload.customer { customQuery ->
                    customQuery.firstName().lastName().email().phone().id()
                }.customerUserErrors { userErrorQuery ->
                    userErrorQuery.field().message()
                }
            }
        }

    override fun generateSingInQuery(userInfo: SignInUserInfo): Storefront.MutationQuery =
        Storefront.mutation { rootQuery ->
            rootQuery.customerAccessTokenCreate(createSingInCustomerCreateInput(userInfo)) { payload ->
                payload.customerAccessToken { customQuery ->
                    customQuery.accessToken().expiresAt()
                }.customerUserErrors { userErrorQuery ->
                    userErrorQuery.field().message()
                }
            }
        }

    override fun generateBrandQuery(): Storefront.QueryRootQuery? =
        Storefront.query { rootQuery: Storefront.QueryRootQuery ->
            rootQuery.collections({ arg -> arg.first(20) }) { collectionConnectionQuery ->
                collectionConnectionQuery.edges { collectionEdgeQuery ->
                    collectionEdgeQuery.node { collectionQuery ->
                        collectionQuery.title().image {
                            it.url()
                        }
                    }
                }
            }
        }

    override fun generateProductByBrandQuery(brandName: String): Storefront.QueryRootQuery =
        Storefront.query { rootQuery: Storefront.QueryRootQuery ->
            rootQuery.collections({ args ->
                args.query(brandName).first(1)
            }) { collectionConnectionQuery ->
                collectionConnectionQuery.edges { collectionEdgeQuery ->
                    collectionEdgeQuery.node { collectionQuery ->
                        collectionQuery.products({ args -> args.first(10) }) { product ->
                            product.edges { productEdge ->
                                productEdge.node { productNode ->
                                    productNode.title()
                                    productNode.description()
                                    productNode.images({ args -> args.first(5) }) { imageConnectionQuery ->
                                        imageConnectionQuery
                                            .edges { imageEdgeQuery ->
                                                imageEdgeQuery
                                                    .node { imageQuery ->
                                                        imageQuery.url()
                                                    }

                                            }
                                    }
                                    productNode.variants({ args -> args.first(5) }) { productVariant ->
                                        productVariant.edges { variantEdge ->
                                            variantEdge.node { variantNode ->
                                                variantNode.availableForSale()
                                                variantNode.price { price ->
                                                    price.amount()
                                                    price.currencyCode()
                                                }

                                            }

                                        }

                                    }
                                }
                            }

                        }
                    }
                }
            }
        }


    private fun createSingUpCustomerCreateInput(userInfo: SignUpUserInfo) =
        Storefront.CustomerCreateInput(userInfo.email, userInfo.password)
            .setFirstName(userInfo.firstName).setLastName(userInfo.lastName)
            .setAcceptsMarketing(true).setPhone(userInfo.phone)


    private fun createSingInCustomerCreateInput(signInUserInfo: SignInUserInfo) =
        Storefront.CustomerAccessTokenCreateInput(
            signInUserInfo.email,
            signInUserInfo.password
        )

}