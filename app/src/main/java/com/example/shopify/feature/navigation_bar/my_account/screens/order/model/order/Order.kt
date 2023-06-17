package com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order

import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.MailingAddress
import com.shopify.buy3.Storefront.MoneyV2
import org.joda.time.DateTime
import java.io.Serializable

data class Order(
    val orderNumber: Int = 0,
    val processedAt: DateTime = DateTime.now(),
    val subTotalPrice: MoneyV2 = MoneyV2(),
    val totalShippingPrice: MoneyV2 = MoneyV2(),
    val discountApplications: MoneyV2 = MoneyV2(),
    val totalTax: MoneyV2 = MoneyV2(),
    val totalPrice: MoneyV2 = MoneyV2(),
    val billingAddress: MailingAddress? = MailingAddress(),
    val lineItems: List<LineItems> = listOf(),
    val fulfillment: Storefront.OrderFulfillmentStatus = Storefront.OrderFulfillmentStatus.ON_HOLD,
    val financialStatus: Storefront.OrderFinancialStatus = Storefront.OrderFinancialStatus.PAID
) : Serializable
