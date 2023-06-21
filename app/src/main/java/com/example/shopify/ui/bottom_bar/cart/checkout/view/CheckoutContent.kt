package com.example.shopify.ui.bottom_bar.cart.checkout.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.R
import com.example.shopify.ui.common.topbar.NamedTopAppBar
import com.example.shopify.ui.common.component.RemoteErrorHeader
import com.example.shopify.ui.address.AddressGraph
import com.example.shopify.ui.bottom_bar.cart.cart.view.componenet.total_cost.TotalCostCard
import com.example.shopify.ui.bottom_bar.cart.checkout.view.component.CheckoutFooterScreen
import com.example.shopify.ui.bottom_bar.cart.checkout.view.component.OrderItemScreen
import com.example.shopify.ui.bottom_bar.cart.checkout.view.component.PaymentMethodScreen
import com.example.shopify.ui.bottom_bar.cart.checkout.view.component.ShipToCard
import com.example.shopify.ui.bottom_bar.cart.checkout.view.event.CheckoutEvent
import com.example.shopify.ui.bottom_bar.cart.checkout.view.state.CheckoutState
import com.example.shopify.ui.theme.ShopifyTheme

@Composable
fun CheckoutContent(
    state: CheckoutState,
    onEvent: (CheckoutEvent) -> Unit,
    navigateTo: (String) -> Unit,
    back: () -> Unit
) {
    val cart = state.cart
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        NamedTopAppBar(back = back)
        RemoteErrorHeader(error = state.remoteError?.asString())
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                ShipToCard(
                    name = cart.address.name.asString(),
                    address = cart.address.address.asString(),
                    phone = cart.address.phone.asString(),
                    onChangeClick = { navigateTo(AddressGraph.Addresses.withArgs("true", "ture")) }
                )
            }
            item {
                PaymentMethodScreen(
                    selected = state.selectedPaymentMethod,
                    paymentMethodChanged = { onEvent(CheckoutEvent.PaymentMethodChanged(it)) }
                )
            }
            item {
                TotalCostCard(
                    itemsCount = cart.lines.size,
                    subTotalsPrice = "${cart.currencyCode} ${cart.subTotalsPrice}",
                    shippingFee = cart.shippingFee,
                    taxes ="${cart.currencyCode} ${cart.taxes}",
                    discounts = cart.discounts,
                    totalPrice = "${cart.currencyCode} ${cart.totalPrice}"
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.order_summery),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(12.dp)
                )
            }
            items(cart.lines) {
                OrderItemScreen(cartLine = it)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        CheckoutFooterScreen(
            totalItems = cart.lines.size,
            totalPrice = "${cart.currencyCode} ${cart.totalPrice}",
            onPlaceOrderClick = { onEvent(CheckoutEvent.PlaceOrder) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckoutContent() {
    ShopifyTheme {
        CheckoutContent(CheckoutState(), {}, {}, {})
    }
}
