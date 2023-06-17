package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.LineItems
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.theme.shopifyColors
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import org.joda.time.DateTime

@Composable
fun OrderDetailsInfoCard(order: Order) {
    Card() {
        Column {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                //order id
                Text(
                    text = "${stringResource(id = R.string.order_id)}  ${
                        order.orderNumber.toString()
                    }",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold


                )
                Spacer(modifier = Modifier.height(2.dp))
                //order date
                Text(
                    text = "${stringResource(id = R.string.placed_on_date)}  ${
                        order.processedAt.toString(
                            "MMMM dd, yyyy"
                        )
                    }",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
            Divider(thickness = 1.dp, modifier = Modifier.padding(10.dp))
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(id = R.string.ship_address),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Row(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "location",
                        modifier = Modifier
                            .padding(
                                start = 4.dp, end = 16.dp
                            )
                            .align(Alignment.CenterVertically)
                    )
                    Column() {
                        Text(
                            text = stringResource(R.string.home),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                        Text(
                            text = order.billingAddress?.address1.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
            Divider(thickness = 1.dp, modifier = Modifier.padding(8.dp))
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(R.string.phone),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Row() {
                    Text(
                        text = order.billingAddress?.phone.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 3.dp, top = 16.dp))
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "verify",
                        tint = MaterialTheme.shopifyColors.DarkGreenColor
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderDetailsInfo() {
    ShopifyTheme() {
        OrderDetailsInfoCard(
            order = Order(
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
                    )
                ),
                fulfillment = Storefront.OrderFulfillmentStatus.FULFILLED,
                financialStatus = Storefront.OrderFinancialStatus.PAID
            )
        )

    }
}