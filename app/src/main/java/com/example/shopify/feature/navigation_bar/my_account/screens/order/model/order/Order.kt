package com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order

import com.shopify.buy3.Storefront.MailingAddress
import com.shopify.buy3.Storefront.MoneyV2
import org.joda.time.DateTime

data class Order(
    val orderNumber: Int = 0,
    val processedAt: DateTime = DateTime.now(),
    val subTotalPrice: MoneyV2 = MoneyV2(),
    val totalShippingPrice: MoneyV2 = MoneyV2(),
    val discountApplications: MoneyV2 = MoneyV2(),
    val totalTax: MoneyV2 = MoneyV2(),
    val totalPrice: MoneyV2 = MoneyV2(),
    val billingAddress: MailingAddress = MailingAddress(),
    // val cancelReason: OrderCancelReason,
    val lineItems: List<LineItems> = listOf()
)
