package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.feature.navigation_bar.cart.model.CartLine
import com.example.shopify.feature.navigation_bar.cart.model.CartProduct
import com.example.shopify.theme.ShopifyTheme
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

@Composable
fun ReviewOrderScreens(
    ordersItems: List<CartLine>
) {
    Column(modifier = Modifier
        .padding(16.dp)) {
        Text(
            text = "Review Your Orders",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            items(ordersItems) {
                OrderItemScreen(cartLine = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    ShopifyTheme {
        ReviewOrderScreens(
            ordersItems = listOf(
                CartLine(
                    productVariantID = ID(""),
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

                CartLine(
                    productVariantID = ID(""),
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
                )
            )
        )
    }
}