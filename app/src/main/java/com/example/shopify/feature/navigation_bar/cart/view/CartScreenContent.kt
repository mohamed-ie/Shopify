package com.example.shopify.feature.navigation_bar.cart.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.cart.model.CartItem
import com.example.shopify.feature.navigation_bar.cart.model.CartProduct
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartItemCard
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartItemEvent
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartItemState
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponCard
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponEvent
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponState
import com.example.shopify.feature.navigation_bar.cart.view.componenet.footer.CartFooter
import com.example.shopify.feature.navigation_bar.cart.view.componenet.header.CartHeader
import com.example.shopify.feature.navigation_bar.cart.view.componenet.total_cost.TotalCostCard
import com.example.shopify.theme.Green170
import com.example.shopify.theme.ShopifyTheme

val cartElevation = 2.dp

@Composable
fun CartScreenContent(
    innerPadding: PaddingValues,
    state: CartState,
    itemsState: List<CartItemState>,
    couponState: CartCouponState,
    onCartItemEvent: (CartItemEvent) -> Unit,
    onCouponEvent: (CartCouponEvent) -> Unit,
    navigateTo: (route: String, navOptionBuilder: (() -> Unit)?) -> Unit
) {
    val cart = state.cart
    val itemsCount = cart.items.size
    Column(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        CartHeader(
            itemsCount = itemsCount,
            address = state.address,
            navigateToWishlistScreen = {
                //navigateTo()
            },
            navigateToMapScreen = {
                //navigateTo()
            }
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(cart.items) { index, cartItem ->
                CartItemCard(
                    state = itemsState[index],
                    cartItem = cartItem,
                    toggleQuantitySelectorVisibility = {onCartItemEvent(CartItemEvent.ToggleQuantitySelectorVisibility) },
                    removeFromCart = { onCartItemEvent(CartItemEvent.RemoveItem(cartItem.id)) },
                    moveToWishlist = { onCartItemEvent(CartItemEvent.MoveToWishlist(cartItem.id)) },
                    quantitySelected = { onCartItemEvent(CartItemEvent.QuantityChanged(it)) }
                )
            }

            item {
                CartCouponCard(
                    state = couponState,
                    applyCoupon = { onCouponEvent(CartCouponEvent.Apply) },
                    clearCoupon = { onCouponEvent(CartCouponEvent.Clear) },
                    couponValueChanged = {onCouponEvent(CartCouponEvent.ValueChanged(it))}
                )
            }

            item {
                TotalCostCard(
                    itemsCount = itemsCount,
                    subTotalsPrice = cart.subTotalsPrice,
                    shippingFee = cart.shippingFee,
                    taxes = cart.taxes,
                    totalPrice = cart.totalPrice
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.mastercard_logo),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.visa_logo),
                        contentDescription = null
                    )

                    Text(
                        text = stringResource(id = R.string.cash),
                        color = Green170,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
        }
        CartFooter(
            itemsCount = itemsCount,
            totalPrice = cart.totalPrice,
            checkout = {
                // navigate to confirm location
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCartScreenContent() {
    ShopifyTheme {
        val cart = Cart(
            items = listOf(
                CartItem(
                    id = "",
                    priceAfterDiscount = "EGP 372.00",
                    priceBeforeDiscount = "EGP 750.00",
                    discount = "50%",
                    quantity = 1,
                    availableQuantity = 5,
                    cartProduct = CartProduct(
                        name = "Pro Airpods Compatible With Android iPhone White",
                        collection = "Generic",
                        thumbnail = "https://m.media-amazon.com/images/I/51ujve2qY8L._AC_SY741_.jpg",
                        vendor = "Egyptian German"
                    )
                ),
                CartItem(
                    id = "",
                    priceAfterDiscount = "EGP 900.00",
                    priceBeforeDiscount = "EGP 1000.00",
                    discount = "10%",
                    quantity = 1,
                    availableQuantity = 20,
                    cartProduct = CartProduct(
                        name = "Snpurdiri 60% Wired Gaming Keyboard, RGB Backlit Ultra-Compact Mini Keyboard, Waterproof Small Compact 61 Keys Keyboard for PC/Mac Gamer, Typist, Travel, Easy to Carry on Business Trip(Black-White)",
                        collection = "Electronics",
                        thumbnail = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMSOfds9U-FZS1k7vZ01-SA6M7MxN-esvkFAkxePEN5V4EUU1nejc1i9vMm8D274FXBQM",
                        vendor = "Amazon"
                    )
                ),
            ),
            "EGP 303.00",
            "EGP 1272.00",
            "FREE",
            "EGP 1575.50"
        )
        CartScreenContent(
            innerPadding = PaddingValues(),
            state = CartState(cart),
            itemsState = listOf(
                CartItemState(),
                CartItemState(chooseQuantityOpened = true, selectedQuantity = 3)
            ),
            couponState = CartCouponState()
        ,{},{}){_,_->

        }
    }
}