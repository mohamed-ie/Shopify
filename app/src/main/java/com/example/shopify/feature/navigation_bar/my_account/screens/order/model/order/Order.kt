package com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order

import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.MailingAddress
import com.shopify.buy3.Storefront.MoneyV2
import com.shopify.buy3.Storefront.OrderCancelReason
import org.joda.time.DateTime

data class Order(
    val orderNumber: Int,
    val billingAddress: MailingAddress,
    val cancelReason: OrderCancelReason,
    val processedAt: DateTime,
    val totalPrice: MoneyV2,
    val lineItems: Storefront.OrderLineItemConnection
)
