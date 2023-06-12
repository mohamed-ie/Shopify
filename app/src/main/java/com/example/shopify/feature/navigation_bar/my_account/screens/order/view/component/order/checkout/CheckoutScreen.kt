package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopify.feature.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.cart.model.CartLine
import com.example.shopify.feature.navigation_bar.cart.model.CartProduct
import com.example.shopify.feature.navigation_bar.my_account.screens.order.OrderViewModel
import com.example.shopify.theme.ShopifyTheme
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

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
            ordersItems =
                listOf(
                    CartLine(
                        id = ID(""),
                        Storefront.MoneyV2().setAmount("372.00")
                            .setCurrencyCode(Storefront.CurrencyCode.EGP),
                        quantity = 1,
                        availableQuantity = 5,
                        cartProduct = CartProduct(
                            name = "Pro Airpods Compatible With Android iPhone White",
                            collection = "Generic",
                            thumbnail = "https://m.media-amazon.com/images/I/51ujve2qY8L._AC_SY741_.jpg",
                            vendor = "Egyptian German"
                        )
                    ),
                    CartLine(
                        id = ID(""),
                        Storefront.MoneyV2().setAmount("900.00")
                            .setCurrencyCode(Storefront.CurrencyCode.EGP),
                        quantity = 1,
                        availableQuantity = 20,
                        cartProduct = CartProduct(
                            name = "Snpurdiri 60% Wired Gaming Keyboard, RGB Backlit Ultra-Compact Mini Keyboard, Waterproof Small Compact 61 Keys Keyboard for PC/Mac Gamer, Typist, Travel, Easy to Carry on Business Trip(Black-White)",
                            collection = "Electronics",
                            thumbnail = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMSOfds9U-FZS1k7vZ01-SA6M7MxN-esvkFAkxePEN5V4EUU1nejc1i9vMm8D274FXBQM",
                            vendor = "Amazon"
                        )
                    ),
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