package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.shopify.feature.navigation_bar.cart.model.CartItem
import com.example.shopify.feature.navigation_bar.cart.model.CartProduct
import com.example.shopify.theme.ShopifyTheme

@Composable
fun ReviewOrderScreens(
    ordersItems: List<CartItem>
) {
    Column(modifier = Modifier
        .fillMaxWidth()
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
                OrderItemScreen(cartItem = it)
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
                CartItem(
                    id = "",
                    priceAfterDiscount = "EGP 372.00",
                    priceBeforeDiscount = "EGP 750.00",
                    discount = "50%",
                    quantity = 5,
                    availableQuantity = 10,
                    cartProduct = CartProduct(
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
                    cartProduct = CartProduct(
                        name = "Pro Airpods Compatible With Android iPhone White",
                        collection = "Generic",
                        thumbnail = "https://m.media-amazon.com/images/I/51ujve2qY8L._AC_SY741_.jpg",
                        vendor = "Egyptian German"
                    )
                )
            )
        )
    }
}