package com.example.shopify.helpers.shopify.query_generator

import com.example.shopify.BuildConfig
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.CheckoutCreateInput
import com.shopify.buy3.Storefront.CheckoutLineItemInput
import com.shopify.buy3.Storefront.CreditCardPaymentInputV2
import com.shopify.buy3.Storefront.CustomerQuery
import com.shopify.buy3.Storefront.CustomerQuery.OrdersArguments
import com.shopify.buy3.Storefront.MutationQuery
import com.shopify.buy3.Storefront.OrderConnectionQuery
import com.shopify.buy3.Storefront.OrderEdgeQuery
import com.shopify.buy3.Storefront.OrderQuery
import com.shopify.buy3.Storefront.QueryRootQuery
import com.shopify.graphql.support.ID
import com.shopify.graphql.support.Input
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

    override fun generateUserOrdersQuery(): Storefront.QueryRootQuery =
        Storefront.query { root: QueryRootQuery ->
            root.customer(BuildConfig.ACCESS_TOKEN) { customer: CustomerQuery ->
                customer.orders({ arg: OrdersArguments -> arg.first(10) }
                ) { connection: OrderConnectionQuery ->
                    connection.edges { edge: OrderEdgeQuery ->
                        edge.node { node: OrderQuery ->
                            node.orderNumber()
                            node.billingAddress {
                                it.address1()
                                it.address1()
                                it.name()
                                it.phone()
                            }
                            node.cancelReason()
                            node.processedAt()
                            node.totalPrice { price ->
                                price.amount()
                                price.currencyCode()
                            }
                            node.lineItems { items ->
                                items.edges { itemsEdge ->
                                    itemsEdge.node { itemsNode ->
                                        itemsNode.variant { variant ->
                                            variant.product { product ->
                                                product.title()
                                                product.images { productImage ->
                                                    productImage.edges { imgEdge ->
                                                        imgEdge.node { imgNode ->
                                                            imgNode.url()
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
            }
        }

    override fun checkoutCreate(cart: Cart): Storefront.MutationQuery {
        var input = CheckoutCreateInput()
            .setLineItemsInput(
                Input.value(
                    listOf(
                        CheckoutLineItemInput(cart.totalQuantity, ID("")),
                    )
                )
            )
        val query = Storefront.mutation { mutationQuery ->
            mutationQuery.checkoutCreate(input) { createPayloadQuery ->
                createPayloadQuery.checkout {
                }
            }
        }
        return query

    }


    override fun generateProductDetailsQuery(id: String): Storefront.QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.node(ID(id)) { nodeQuery ->
                nodeQuery.onProduct { productQuery ->
                    productQuery
                        .title()
                        .vendor()
                        .description()
                        .totalInventory()
                        .priceRange { productPriceRangeQuery ->
                            productPriceRangeQuery.minVariantPrice { moneyV2Query ->
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
                        .variants({ variantsArguments -> variantsArguments.first(5) }) { productVariantConnectionQuery ->
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

    override fun generateServerUrlQuery(): Storefront.QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.shop { shopQuery ->
                shopQuery.paymentSettings { paymentSettings -> paymentSettings.cardVaultUrl() }
            }
        }

    override fun generatePaymentCompletionAvailabilityQuery(
        checkoutId: ID,
        input: CreditCardPaymentInputV2
    ): MutationQuery =
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
            .setFirstName(userInfo.firstName).setLastName(userInfo.lastName)
            .setAcceptsMarketing(true).setPhone(userInfo.phone)


    private fun createSingInCustomerCreateInput(signInUserInfo: SignInUserInfo) =
        Storefront.CustomerAccessTokenCreateInput(
            signInUserInfo.email,
            signInUserInfo.password
        )

}