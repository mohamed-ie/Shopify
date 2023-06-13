package com.example.shopify.helpers.shopify.query_generator

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.CartBuyerIdentityInput
import com.shopify.buy3.Storefront.CartLineUpdateInput
import com.shopify.buy3.Storefront.CartQuery
import com.shopify.buy3.Storefront.CheckoutCreateInput
import com.shopify.buy3.Storefront.CheckoutLineItemInput
import com.shopify.buy3.Storefront.CreditCardPaymentInputV2
import com.shopify.buy3.Storefront.CustomerAddressCreatePayloadQuery
import com.shopify.buy3.Storefront.CustomerQuery
import com.shopify.buy3.Storefront.CustomerQuery.OrdersArguments
import com.shopify.buy3.Storefront.MailingAddressQuery
import com.shopify.buy3.Storefront.MutationQuery
import com.shopify.buy3.Storefront.OrderConnectionQuery
import com.shopify.buy3.Storefront.OrderEdgeQuery
import com.shopify.buy3.Storefront.OrderQuery
import com.shopify.buy3.Storefront.QueryRootQuery
import com.shopify.graphql.support.ID
import com.shopify.graphql.support.Input
import javax.inject.Inject

class ShopifyQueryGeneratorImpl @Inject constructor() : ShopifyQueryGenerator {
    override fun generateSingUpQuery(userInfo: SignUpUserInfo): MutationQuery =
        Storefront.mutation { rootQuery ->
            rootQuery.customerCreate(createSingUpCustomerCreateInput(userInfo)) { payload ->
                payload.customer { customQuery ->
                    customQuery.firstName().lastName().email().phone().id()
                }.customerUserErrors { userErrorQuery ->
                    userErrorQuery.field().message()
                }
            }
        }

    override fun generateSingInQuery(userInfo: SignInUserInfo): MutationQuery =
        Storefront.mutation { rootQuery ->
            rootQuery.customerAccessTokenCreate(createSingInCustomerCreateInput(userInfo)) { payload ->
                payload.customerAccessToken { customQuery ->
                    customQuery.accessToken().expiresAt()
                }.customerUserErrors { userErrorQuery ->
                    userErrorQuery.field().message()
                }
            }
        }

    override fun generateBrandQuery(): QueryRootQuery? =
        Storefront.query { rootQuery: QueryRootQuery ->
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

    override fun generateProductByBrandQuery(brandName: String): QueryRootQuery =
        Storefront.query { rootQuery: QueryRootQuery ->
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

    override fun generateUserOrdersQuery(accessToken: String): QueryRootQuery =
        Storefront.query { root: QueryRootQuery ->
            root.customer(accessToken) { customer: CustomerQuery ->
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

    override fun checkoutCreate(cart: Cart, email: String): MutationQuery {
        val lines = cart.lines.map {
            CheckoutLineItemInput(it.quantity, it.productVariantID)
        }
        val input = CheckoutCreateInput()
            .setLineItemsInput(Input.value(lines))
            .setEmail(email)
        val query = Storefront.mutation { mutationQuery ->
            mutationQuery.checkoutCreate(input) { createPayloadQuery ->
                createPayloadQuery.checkout { checkoutQuery ->
                    checkoutQuery.ready()
                    checkoutQuery.buyerIdentity {
                        it.countryCode()
                    }
                    checkoutQuery.email()
                }.checkoutUserErrors {
                    it.message()
                }
            }
        }
        return query

    }

    override fun generateProductCategoryQuery(productType: String, productTag: String):
            QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.products({ args ->
                args.query("product_type:$productType tag:$productTag")
                    .first(20)
            }) { productConnectionQuery ->
                productConnectionQuery.edges { productEdgeQuery ->
                    productEdgeQuery.node { productNode ->
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

    override fun generateProductTagsQuery(): QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.productTags(25) { productTag ->
                productTag.edges {
                    it.node()
                }
            }
        }

    override fun generateProductTypesQuery(): QueryRootQuery =
        Storefront.query { rootQuery ->
            rootQuery.productTypes(20) { productType ->
                productType.edges {
                    it.node()
                }
            }
        }

    override fun generateProductDetailsQuery(id: String): QueryRootQuery =
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

    override fun generateServerUrlQuery(): QueryRootQuery =
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

    override fun generateUpdateCheckoutReadyQuery(paymentId: ID): QueryRootQuery =
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

    override fun generateDeleteAddressQuery(addressId: ID, accessToken: String): MutationQuery =
        Storefront.mutation { mutation: MutationQuery ->
            mutation.customerAddressDelete(
                addressId,
                accessToken,
            ) { it.customerUserErrors { it.message() } }
        }

    override fun generateGetMinCustomerInfoQuery(accessToken: String): QueryRootQuery =
        Storefront.query { query ->
            query.customer(accessToken) { customer ->
                customer.firstName()
                    .email()
                    .metafield("settings", "currency") { it.value() }
            }
        }

    override fun generateCreateCustomerCartQuery(
        accessToken: String,
        productVariantId: ID,
        quantity: Int
    ): MutationQuery {
        val input = Storefront.CartInput().apply {
            //set buyer access token
            buyerIdentity = CartBuyerIdentityInput()
                .setCustomerAccessToken(accessToken)
            //add new line
            val line = Storefront.CartLineInput(productVariantId)
                .setQuantity(quantity)
            lines = listOf(line)
        }

        return Storefront.mutation { mutation ->
            mutation.cartCreate({ args -> args.input(input) }) { cartCreate ->
                cartCreate.cart {}.userErrors { it.message() }
            }
        }
    }

    override fun generateAddCartLineQuery(
        cartId: ID,
        productVariantId: ID,
        quantity: Int
    ): MutationQuery = Storefront.mutation { mutation ->
        val line = Storefront.CartLineInput(productVariantId)
            .setQuantity(quantity)

        val input = listOf(line)
        mutation.cartLinesAdd(input, cartId) { cartLinesAdd ->
            cartLinesAdd.userErrors { it.message() }
        }
    }

    override fun generateGetCartQuery(cartId: ID): QueryRootQuery =
        Storefront.query { query ->
            query.cart(cartId) { it.queryCart() }
        }

    override fun generateAddressesQuery(accessToken: String): QueryRootQuery =
        Storefront.query { query ->
            query.customer(accessToken) { customer ->
                customer.addresses({ args -> args.first(250) }) { addresses ->
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

    override fun generateRemoveCartLineQuery(cartId: ID, linesId: List<ID>): MutationQuery =
        Storefront.mutation { mutation ->
            mutation.cartLinesRemove(cartId, linesId) { cartLinesRemove ->
                cartLinesRemove
                    .cart { it.queryCart() }
                    .userErrors { it.message() }
            }
        }

    override fun generateChangeCartLineQuantityQuery(
        cartId: ID,
        merchandiseId: ID,
        quantity: Int
    ): MutationQuery =
        Storefront.mutation { mutation ->
            val lineUpdate = CartLineUpdateInput(merchandiseId)
                .setQuantity(quantity)
            mutation.cartLinesUpdate(cartId, listOf(lineUpdate)) { cartLinesUpdate ->
                cartLinesUpdate
                    .cart { it.queryCart() }
                    .userErrors { it.message() }
            }
        }

    override fun generateApplyCouponQuery(cartId: ID, coupon: String): MutationQuery =
        Storefront.mutation { mutation ->
            mutation.cartDiscountCodesUpdate(
                cartId,
                { it.discountCodes(listOf(coupon)) }) { cartDiscountCodesUpdate ->
                cartDiscountCodesUpdate.cart { it.queryCart() }
                    .userErrors { it.message() }
            }
        }

    override fun generateUpdateCartAddress(
        accessToken: String,
        cartId: ID,
        addressId: ID
    ): MutationQuery =
        Storefront.mutation { mutation ->
            val deliveryAddress = Storefront.DeliveryAddressInput()
                .setCustomerAddressId(addressId)
            val input = CartBuyerIdentityInput()
                .setCustomerAccessToken(accessToken)
                .setDeliveryAddressPreferences(listOf(deliveryAddress))
            mutation.cartBuyerIdentityUpdate(cartId, input) { cartBuyerIdentityUpdate ->
                cartBuyerIdentityUpdate.userErrors { it.message() }
            }
        }

    private fun MailingAddressQuery.queryAddress() = run {
        address1()
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

    private fun CartQuery.queryCart(after: String? = null) = run {
        cost { cost ->
            cost.checkoutChargeAmount { it.amount().currencyCode() }
                .subtotalAmount { it.amount().currencyCode() }
                .totalAmount { it.amount().currencyCode() }
                .totalDutyAmount { it.amount().currencyCode() }
                .totalTaxAmount { it.amount().currencyCode() }
        }
            .lines({ args ->
                args.first(250)
                    .after(after)
            }) { lines ->
                lines.edges { edges ->
                    edges.node { node ->
                        node.id()
                            .quantity()
                            .merchandise { merchandise ->
                                merchandise.onProductVariant { productVariant ->
                                    productVariant.price { it.amount().currencyCode() }
                                        .image { it.url() }
                                        .quantityAvailable()
                                        .product { product ->
                                            product.title()
                                                .vendor()
                                                .productType()
                                        }
                                }
                            }
                    }
                }.pageInfo { it.hasNextPage() }
            }
            .discountCodes { it.code() }
            .discountAllocations { discountAllocations ->
                discountAllocations.discountedAmount {
                    it.amount().currencyCode()
                }
            }
            .buyerIdentity { buyerIdentity ->
                buyerIdentity.deliveryAddressPreferences { deliveryAddressPreferences ->
                    deliveryAddressPreferences
                        .onMailingAddress { it.queryAddress() }
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