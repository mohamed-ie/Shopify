package com.example.shopify.helpers.shopify.query_generator

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.CreditCardPaymentInputV2
import com.shopify.buy3.Storefront.CustomerAddressCreatePayloadQuery
import com.shopify.buy3.Storefront.MutationQuery
import com.shopify.graphql.support.ID
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

    override fun generateCreateAddress(
        accessToken: String,
        address: Storefront.MailingAddressInput
    ): MutationQuery =
        Storefront.mutation { mutation: MutationQuery ->
            mutation.customerAddressCreate(
                accessToken,
                address
            ) { query: CustomerAddressCreatePayloadQuery ->
                query.customerAddress { }.customerUserErrors { it.message() }
            }
        }

    override fun generateDeleteAddressQuery(addressId: String, accessToken: String): MutationQuery =
        Storefront.mutation { mutation: MutationQuery ->
            mutation.customerAddressDelete(
                ID(addressId),
                accessToken,
            ) { it.customerUserErrors { it.message() } }
        }

    override fun generateGetMinCustomerInfoQuery(accessToken: String): Storefront.QueryRootQuery =
        Storefront.query { query ->
            query.customer(accessToken) { customer ->
                customer.firstName()
                    .email()
                    .metafield("settings", "currency") { it.value() }
            }
        }

    override fun generateAddressQuery(accessToken: String): Storefront.QueryRootQuery =
        Storefront.query { query ->
            query.customer(accessToken) { customer ->
                customer.addresses({args->args.first(250)}) { addresses ->
                    addresses.edges { edges ->
                        edges.node { node ->
                            node.address1()
                                .address2()
                                .company()
                                .country()
                                .city()
                                .province()
                                .zip()
                                .lastName()
                                .firstName()
                                .phone()
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