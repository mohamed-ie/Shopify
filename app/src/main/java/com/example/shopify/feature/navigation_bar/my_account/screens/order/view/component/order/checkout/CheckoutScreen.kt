package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopify.feature.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.cart.model.CartItem
import com.example.shopify.feature.navigation_bar.cart.model.Product
import com.example.shopify.feature.navigation_bar.my_account.screens.order.OrderViewModel
import com.example.shopify.theme.ShopifyTheme

@Composable
fun CheckoutScreen(
    cart: Cart,
    viewModel: OrderViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        NamedTopAppBar("", {})
        ShipToCard(
            address = "galal street - elhassan building - haram - giza - egypt ",
            phoneNumber = "+201117513385"
        ) {
            // navigate to addresses screen
        }
        PaymentMethodScreen()

        OrderSummaryScreen()

        ReviewOrderScreens(
            ordersItems = listOf(
                CartItem(
                    id = "",
                    priceAfterDiscount = "EGP 372.00",
                    priceBeforeDiscount = "EGP 750.00",
                    discount = "50%",
                    quantity = 5,
                    availableQuantity = 10,
                    product = Product(
                        name = "Pro Airpods Compatible With Android iPhone White",
                        collection = "Generic",
                        thumbnail = "https://m.media-amazon.com/images/I/51ujve2qY8L._AC_SY741_.jpg",
                        vendor = "Egyptian German"
                    )
                ),

                CartItem(
                    id = "",
                    priceAfterDiscount = "EGP 372.00",
                    priceBeforeDiscount = "EGP 750.00",
                    discount = "50%",
                    quantity = 5,
                    availableQuantity = 10,
                    product = Product(
                        name = "Pro Airpods Compatible With Android iPhone White",
                        collection = "Generic",
                        thumbnail = "https://m.media-amazon.com/images/I/51ujve2qY8L._AC_SY741_.jpg",
                        vendor = "Egyptian German"
                    )
                )
            )
        )
        CheckoutFooterScreen(totalItems = 5, totalPrice = "EGY 1555") {
            viewModel.getCheckOutID(cart)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCheckOutScreen() {
    ShopifyTheme {
        CheckoutScreen(cart = Cart(), hiltViewModel())
    }
}