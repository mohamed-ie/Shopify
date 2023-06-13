package com.example.shopify.helpers.shopify.mapper

import com.example.shopify.feature.address.addresses.model.MyAccountMinAddress
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.cart.model.CartLine
import com.example.shopify.feature.navigation_bar.cart.model.CartProduct
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandVariants
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment.ShopifyCreditCardPaymentStrategy
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Discount
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Price
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.VariantItem
import com.example.shopify.helpers.UIError
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphError
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.ImageConnection
import com.shopify.graphql.support.ID
import javax.inject.Inject

class ShopifyMapperImpl @Inject constructor() : ShopifyMapper {

    override fun map(response: GraphResponse<Storefront.Mutation>): SignUpUserResponseInfo {
        val customer = response.data?.customerCreate?.customer
        return SignUpUserResponseInfo(
            customer?.firstName ?: "",
            customer?.lastName ?: "",
            customer?.email ?: "",
            customer?.phone ?: "",
            customer?.id.toString(),
            response.errors.getOrNull(0)?.message()
                ?: response.data?.customerCreate?.customerUserErrors?.getOrNull(0)?.message
        )
    }

    override fun mapToSignInResponse(
        response: GraphResponse<Storefront.Mutation>,
        signInUserInfo: SignInUserInfo
    ): SignInUserInfoResult {
        val customerAccessToken = response.data?.customerAccessTokenCreate?.customerAccessToken
        return SignInUserInfoResult(
            signInUserInfo.email,
            signInUserInfo.password,
            customerAccessToken?.accessToken ?: "",
            customerAccessToken?.expiresAt?.toDate()?.time.toString().takeIf { it != "null" },
            response.data?.customerAccessTokenCreate?.customerUserErrors?.getOrNull(0)?.message
                ?: ""
        )
    }

    override fun mapToProduct(response: GraphResponse<Storefront.QueryRoot>): Product =
        (response.data?.node as Storefront.Product).let { storefrontProduct ->
            Product(
                id = storefrontProduct.id,
                title = storefrontProduct.title ?: "",
                description = storefrontProduct.description ?: "",
                totalInventory = storefrontProduct.totalInventory ?: 0,
                vendor = storefrontProduct.vendor ?: "",
                image = storefrontProduct.images?.nodes?.getOrNull(0)?.url ?: "",
                variants = (storefrontProduct.variants?.edges as List<Storefront.ProductVariantEdge>).map { productVariantNode ->
                    productVariantNode.node.let { productVariant ->
                        VariantItem(
                            image = productVariant.image.url,
                            id = productVariant.id,
                            price = productVariant.price.amount,
                            title = productVariant.title
                        )
                    }
                },
                price = Price(
                    amount = storefrontProduct.priceRange.minVariantPrice.amount,
                    currencyCode = storefrontProduct.priceRange.minVariantPrice.currencyCode.name
                ),
                discount = Discount(
                    realPrice = "",
                    percent = 0
                )
            )
        }

    override fun mapToProductsByBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<BrandProduct> {
        val res = response.data?.collections?.edges?.get(0)?.node?.products?.edges?.map {
            BrandProduct(
                id = it.node.id,
                title = it.node.title,
                description = it.node.description,
                images = mapToImageUrl(it.node.images),
                brandVariants = mapToVariant(it.node.variants)
            )
        } ?: listOf()
        return res
    }

    override fun mapToOrderResponse(response: GraphResponse<Storefront.QueryRoot>): List<Order> {
        return response.data?.customer?.orders?.edges?.map {
            Order(
                it.node.orderNumber, it.node.billingAddress,
                it.node.cancelReason, it.node.processedAt, it.node.totalPrice, it.node.lineItems
            )
        } ?: listOf()
    }

    override fun mapToCheckoutId(result: GraphResponse<Storefront.Mutation>): ID? {
        return result.data?.checkoutCreate?.checkout?.id
    }

    override fun mapToProductsCategoryResponse(response: GraphResponse<Storefront.QueryRoot>): List<BrandProduct> {
        return response.data?.products?.edges?.map {
            BrandProduct(
                id = it.node.id,
                title = it.node.title,
                description = it.node.description,
                images = mapToImageUrl(it.node.images),
                brandVariants = mapToVariant(it.node.variants)
            )
        } ?: listOf()
    }

    override fun mapToProductsTypeResponse(response: GraphResponse<Storefront.QueryRoot>): List<String> {
        return response.data?.productTypes?.edges?.map {
            it.node.toString()
        } ?: listOf()
    }

    override fun mapToProductsTagsResponse(response: GraphResponse<Storefront.QueryRoot>): List<String> {
        return response.data?.productTags?.edges?.map {
            it.node.toString()
        } ?: listOf()
    }

    override fun isAddressSaved(response: GraphResponse<Storefront.Mutation>): Boolean =
        response.hasErrors

    override fun isAddressDeleted(response: GraphResponse<Storefront.Mutation>): Boolean {
        return response.data?.customerAddressDelete?.customerUserErrors?.isEmpty() ?: false
    }

    override fun mapToMinCustomerInfo(graphResponse: GraphResponse<Storefront.QueryRoot>): MinCustomerInfo {
        return graphResponse.data?.customer?.run {
            MinCustomerInfo(name = firstName, email = email)
        } ?: MinCustomerInfo()
    }

    override fun mapToAddresses(response: GraphResponse<Storefront.QueryRoot>): List<MyAccountMinAddress> {
        return response.data?.customer?.addresses?.edges?.map {
            it.node.run {
                MyAccountMinAddress(
                    id = id,
                    name = "$firstName $lastName",
                    address = toAddressString(),
                    phone = phone
                )
            }
        } ?: emptyList()
    }

    override fun mapToCartId(response: GraphResponse<Storefront.Mutation>): Pair<String?, String?>? {
        return response.data?.cartCreate?.run {
            Pair(
                cart.id?.toString(),
                userErrors?.get(0)?.message
            )
        }
    }

    override fun mapToAddCartLine(response: GraphResponse<Storefront.Mutation>): String? {
        return response.data?.cartLinesAdd?.userErrors?.getOrNull(0)?.message
    }

    private fun Storefront.MailingAddress.toAddressString() =
        "$address1, $address2 ,$city, $country, $province ${if (company.isNotBlank()) ",$company" else ""},$zip"


    private fun mapToImageUrl(response: ImageConnection): List<String> {
        return response.edges.map {
            it.node.url
        }
    }

    private fun mapToVariant(response: Storefront.ProductVariantConnection): BrandVariants {
        return response.edges.firstOrNull()?.node.let {
            BrandVariants(it!!.availableForSale, it.price)
        }
    }

    override fun mapToBrandResponse(response: GraphResponse<Storefront.QueryRoot>): List<Brand>? {
        return response.data?.collections?.edges?.drop(1)?.map {
            Brand(title = it.node.title, url = it.node.image?.url)
        }
    }

    override fun map(error: GraphError): UIError =
        when (error) {
            is GraphError.NetworkError -> UIError.NoInternetConnection
            is GraphError.HttpError -> UIError.RequestFailed
            is GraphError.CallCanceledError -> UIError.Canceled
            is GraphError.ParseError, is GraphError.Unknown -> UIError.Unexpected
        }

    override fun mapToPaymentCompletionAvailability(result: GraphResponse<Storefront.Mutation>): ShopifyCreditCardPaymentStrategy.PaymentCompletionAvailability {
        val checkout = result.data?.checkoutCompleteWithCreditCardV2
        val errors = checkout?.checkoutUserErrors
            ?.map { it.message }
            .takeIf { it != null } ?: listOf()
        val checkoutReady = checkout?.checkout?.ready ?: false
        val paymentReady = checkout?.payment?.ready ?: false
        val paymentId = checkout?.payment?.id?.toString()

        return ShopifyCreditCardPaymentStrategy.PaymentCompletionAvailability(
            errors = errors,
            checkoutReady = checkoutReady,
            paymentReady = paymentReady,
            paymentId = paymentId
        )
    }

    override fun mapToPaymentResult(result: GraphCallResult.Success<Storefront.QueryRoot>): ShopifyCreditCardPaymentStrategy.PaymentResult {
        val payment = result.response.data?.node as Storefront.Payment?
        val orderId = payment?.checkout
            ?.order
            ?.id
            ?.toString()

        return ShopifyCreditCardPaymentStrategy.PaymentResult(
            error = payment?.errorMessage,
            orderId = orderId,
            ready = payment?.ready ?: false,
            orderNumber = payment?.checkout?.order?.orderNumber,
            totalPrice = payment?.checkout?.totalPrice?.run { "$currencyCode $amount" }
        )
    }

    override fun mapToCart(graphResponse: GraphResponse<Storefront.QueryRoot>): Cart? =
        graphResponse.data?.cart?.toCart()


    override fun mapToRemoveCartLines(response: GraphResponse<Storefront.Mutation>): Cart? =
        response.data?.cartLinesRemove?.run { cart.toCart(userErrors?.getOrNull(0)?.message) }

    override fun mapToChangeCartLineQuantity(response: GraphResponse<Storefront.Mutation>): Cart? =
        response.data?.cartLinesUpdate?.run { cart.toCart(userErrors?.getOrNull(0)?.message) }

    override fun mapToApplyCouponToCart(response: GraphResponse<Storefront.Mutation>): Cart? =
        response.data?.cartDiscountCodesUpdate?.run {
            cart.toCart(couponError = userErrors.getOrNull(0)?.message)
        }

    private fun Storefront.Cart.toCart(error: String? = null, couponError: String? = null): Cart =
        run {
            val lines = lines?.edges?.map {
                val merchandise = it.node.merchandise as Storefront.ProductVariant
                val product = merchandise.product

                val cartProduct = CartProduct(
                    id = product.id,
                    name = product.title,
                    thumbnail = merchandise.image.url,
                    collection = product.productType,
                    vendor = product.vendor
                )
                CartLine(
                    productVariantID = merchandise.id,
                    id = it.node.id,
                    price = merchandise.price,
                    quantity = it.node.quantity,
                    availableQuantity = merchandise.quantityAvailable,
                    cartProduct = cartProduct
                )
            }
            val cost = cost
            val coupons = discountAllocations
                ?.takeIf { it is Storefront.CartCodeDiscountAllocation }
                ?.map { it as Storefront.CartCodeDiscountAllocation }
                ?.associate { it.code to it.discountedAmount }
                ?: emptyMap()

            val mailingAddress = buyerIdentity.deliveryAddressPreferences.getOrNull(0) as? Storefront.MailingAddress?

            Cart(
                lines = lines ?: emptyList(),
                taxes = cost?.totalTaxAmount,
                subTotalsPrice = cost?.subtotalAmount,
                shippingFee = cost?.totalDutyAmount,
                checkoutPrice = cost?.checkoutChargeAmount,
                totalPrice = cost?.totalAmount,
                discounts = cost?.totalAmount - cost?.totalTaxAmount - cost?.checkoutChargeAmount,
                coupons = coupons,
                address = mailingAddress?.toAddressString()?:"",
                couponError = couponError,
                error = error,
                hasNextPage = this.lines?.pageInfo?.hasNextPage ?: false
            )
        }

    override fun mapToUpdateCartAddress(response: GraphResponse<Storefront.Mutation>): String? =
        response.data?.cartBuyerIdentityUpdate?.userErrors?.getOrNull(0)?.message


    private operator fun Storefront.MoneyV2?.minus(money: Storefront.MoneyV2?): Storefront.MoneyV2? {
        val newAmount = ((this?.amount?.toDouble() ?: 0.0) - (money?.amount?.toDouble() ?: 0.0))
        if (newAmount <= 0.0) return null
        return Storefront.MoneyV2().setAmount(newAmount.toString())
            .setCurrencyCode(this?.currencyCode ?: money?.currencyCode)
    }
}