package com.example.shopify.ui.order.order_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.cart.cart.view.componenet.total_cost.TotalCostCard
import com.example.shopify.ui.common.topbar.NamedTopAppBar
import com.example.shopify.ui.order.OrdersViewModel
import com.example.shopify.ui.order.orders.component.OrderDetailsInfoCard
import com.example.shopify.ui.order.orders.component.OrderDetailsProducts
import com.example.shopify.ui.order.orders.component.PaymentMethodChooser
import com.example.shopify.ui.theme.ShopifyTheme

@Composable
fun OrderDetails(
    viewModel: OrdersViewModel,
    back: () -> Unit
) {
    val order = viewModel.orderList.collectAsState().value[viewModel.orderIndex]
    Scaffold(topBar = { NamedTopAppBar(back = back) }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OrderDetailsInfoCard(order = order)
            }
            items(order.lineItems) {
                OrderDetailsProducts(lineItems = it)
            }
            item {
                Text(
                    text = stringResource(id = R.string.payment_Method),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            item {
                PaymentMethodChooser(paymentStatue = order.financialStatus)
            }
            item {
                Text(
                    text = stringResource(id = R.string.order_summary),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            item {
                TotalCostCard(
                    itemsCount = order.lineItems.size,
                    subTotalsPrice = "${order.subTotalPrice.currencyCode} ${order.subTotalPrice.amount}",
                    shippingFee = "${order.totalShippingPrice.currencyCode} ${order.totalShippingPrice.amount}",
                    taxes = "${order.totalTax.currencyCode} ${order.totalTax.amount}",
                    totalPrice = "${order.totalPrice.currencyCode} ${order.totalPrice.amount}",
                    discounts = order.discountApplications?.run {"$currencyCode $amount"}
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderDetails() {
    ShopifyTheme() {
        /*       OrderDetails(order = Order(
                   orderNumber = 1022,
                   processedAt = DateTime.now(),
                   subTotalPrice = Storefront.MoneyV2().setAmount("50")
                       .setCurrencyCode(Storefront.CurrencyCode.EGP),
                   totalShippingPrice = Storefront.MoneyV2().setAmount("50")
                       .setCurrencyCode(Storefront.CurrencyCode.EGP),
                   discountApplications = Storefront.MoneyV2().setAmount("50")
                       .setCurrencyCode(Storefront.CurrencyCode.EGP),
                   totalTax = Storefront.MoneyV2().setAmount("50")
                       .setCurrencyCode(Storefront.CurrencyCode.EGP),
                   totalPrice = Storefront.MoneyV2().setAmount("50")
                       .setCurrencyCode(Storefront.CurrencyCode.EGP),
                   billingAddress = Storefront.MailingAddress()
                       .setAddress1("haram - glal street - giza = ggypt")
                       .setPhone("+201117513385"),
                   lineItems = listOf<LineItems>(
                       LineItems(
                           id = ID("gid://shopify/Product/8312391237939"),
                           name = "VANS | AUTHENTIC | (MULTI EYELETS) | GRADIENT/CRIMSON",
                           thumbnail = "https://cdn.shopify.com/s/files/1/0774/1662/8531/products/d841f71ea6845bf6005453e15a18c632.jpg?v=1685530920",
                           collection = "SHOES",
                           vendor = "VANS",
                           price = Storefront.MoneyV2().setAmount("50")
                               .setCurrencyCode(Storefront.CurrencyCode.EGP),
                           description = "The forefather of the Vans family, the Vans Authentic was introduced in 1966 and nearly 4 decades later is still going strong, its popularity extending from the original fans - skaters and surfers to all sorts. The Vans Authentic is constructed from canvas and Vans' signature waffle outsole construction."
                       ),
                       LineItems(
                           id = ID("gid://shopify/Product/8312391237939"),
                           name = "VANS | AUTHENTIC | (MULTI EYELETS) | GRADIENT/CRIMSON",
                           thumbnail = "https://cdn.shopify.com/s/files/1/0774/1662/8531/products/d841f71ea6845bf6005453e15a18c632.jpg?v=1685530920",
                           collection = "SHOES",
                           vendor = "VANS",
                           price = Storefront.MoneyV2().setAmount("50")
                               .setCurrencyCode(Storefront.CurrencyCode.EGP),
                           description = "The forefather of the Vans family, the Vans Authentic was introduced in 1966 and nearly 4 decades later is still going strong, its popularity extending from the original fans - skaters and surfers to all sorts. The Vans Authentic is constructed from canvas and Vans' signature waffle outsole construction."
                       )
                   ),
                   fulfillment = Storefront.OrderFulfillmentStatus.FULFILLED,
                   financialStatus = Storefront.OrderFinancialStatus.PAID
               )) {
                   // back
               }*/
    }
}