package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.feature.address.AddressGraph
import com.example.shopify.feature.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.component.CheckoutFooterScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.component.OrderItemScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.component.OrderSummaryScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.component.PaymentMethodScreen
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.component.ShipToCard

@Composable
fun CheckoutContent(
    cart: Cart?,
    navigateTo: (String) -> Unit,
    back: () -> Unit,
    onPlaceOrder: (Cart) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        NamedTopAppBar("", back)
        LazyColumn(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ShipToCard(
                    address = cart?.address ?: "",
                    onChangeClick = { navigateTo(AddressGraph.Addresses.withArgs("true")) }
                )
            }
            item {
                PaymentMethodScreen()
            }
            item {
                OrderSummaryScreen(itemsCount = cart!!.lines.size,
                    discount = cart.discounts ?: "0",
                    shippingFees = cart.shippingFee
                        ?: "Free",
                    subtotal = cart.subTotalsPrice?: "",
                    taxes = cart.taxes?: "EGP0 0",
                    total = cart.totalPrice ?: "")
            }
            item {
                Text(
                    text = "Order Summary",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(cart!!.lines) {
                OrderItemScreen(cartLine = it)
            }
        }
        CheckoutFooterScreen(totalItems = cart?.lines?.size ?: 0,
            totalPrice = cart?.totalPrice ?: "",
            onPlaceOrderClick = {
                onPlaceOrder(cart!!)
            })
    }
}


