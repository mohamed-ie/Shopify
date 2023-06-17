package com.example.shopify.feature.navigation_bar.cart.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.Graph
import com.example.shopify.feature.address.AddressGraph
import com.example.shopify.feature.auth.Auth
import com.example.shopify.feature.navigation_bar.cart.CartGraph
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartItemCard
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartItemEvent
import com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card.CartLineState
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponCard
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponEvent
import com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon.CartCouponState
import com.example.shopify.feature.navigation_bar.cart.view.componenet.footer.CartFooter
import com.example.shopify.feature.navigation_bar.cart.view.componenet.header.CartHeader
import com.example.shopify.feature.navigation_bar.cart.view.componenet.total_cost.TotalCostCard
import com.example.shopify.feature.navigation_bar.common.component.RemoteErrorHeader
import com.example.shopify.theme.Green170

val cartElevation = 2.dp

@Composable
fun CartScreenContent(
    cart: Cart,
    itemsState: List<CartLineState>,
    couponState: CartCouponState,
    onCartItemEvent: (CartItemEvent) -> Unit,
    onCouponEvent: (CartCouponEvent) -> Unit,
    navigateTo: (route: String) -> Unit
) {
    val itemsCount = cart.lines.size
    Column(Modifier.fillMaxSize()) {
        RemoteErrorHeader(error = cart.error)
        CartHeader(
            itemsCount = itemsCount,
            address = cart.address,
            navigateToWishlistScreen = {
                if (cart.isLoggedIn)
                    navigateTo(Graph.WISH_LIST)
                else
                    navigateTo(Auth.SIGN_IN)
            },
            navigateToAddressesScreen = { navigateTo(AddressGraph.Addresses.withArgs("true")) }
        )
        if (cart.lines.isEmpty())
            EmptyCart()
        else
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { }
                itemsIndexed(cart.lines) { index, cartItem ->
                    CartItemCard(
                        state = itemsState[index],
                        cartLine = cartItem,
                        toggleQuantitySelectorVisibility = {
                            onCartItemEvent(
                                CartItemEvent.ToggleQuantitySelectorVisibility(
                                    index
                                )
                            )
                        },
                        removeFromCart = { onCartItemEvent(CartItemEvent.RemoveLine(index)) },
                        moveToWishlist = { onCartItemEvent(CartItemEvent.MoveToWishlist(index)) },
                        quantitySelected = {
                            onCartItemEvent(
                                CartItemEvent.QuantityChanged(index, it)
                            )
                        }
                    )
                }

                item {
                    CartCouponCard(
                        state = couponState,
                        applyCoupon = { onCouponEvent(CartCouponEvent.Apply) },
                        clearCoupon = { onCouponEvent(CartCouponEvent.Clear) },
                        couponValueChanged = { onCouponEvent(CartCouponEvent.ValueChanged(it)) }
                    )
                }

                item {
                    TotalCostCard(
                        itemsCount = itemsCount,
                        subTotalsPrice = cart.subTotalsPrice,
                        shippingFee = cart.shippingFee,
                        taxes = cart.taxes,
                        discounts = cart.discounts,
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
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        CartFooter(
            itemsCount = itemsCount,
            totalPrice = cart.totalPrice,
            checkout = {
                // navigate to place checkout
                navigateTo(CartGraph.CHECK_OUT)
            }
        )
    }
}

@Composable
private fun EmptyCart() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .fillMaxWidth(.4f)
                .aspectRatio(1f),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.no_orders),
            contentDescription = null
        )

        Text(
            text = stringResource(id = R.string.your_cart_is_empty),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = stringResource(id = R.string.be_sure_to_fill_your_cart_with_somthing_you_like),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(2f))
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCartScreenContent() {
//    ShopifyTheme {
//        val cart = Cart(
//            lines = listOf(
//                CartLine(
//                    productVariantID = ID(""),
//                    id = ID(""),
//                    Storefront.MoneyV2().setAmount("372.00")
//                        .setCurrencyCode(Storefront.CurrencyCode.EGP),
//                    quantity = 1,
//                    availableQuantity = 5,
//                    cartProduct = CartProduct(
//                        name = "Pro Airpods Compatible With Android iPhone White",
//                        collection = "Generic",
//                        thumbnail = "https://m.media-amazon.com/images/I/51ujve2qY8L._AC_SY741_.jpg",
//                        vendor = "Egyptian German"
//                    )
//                ),
//                CartLine(
//                    productVariantID = ID(""),
//                    id = ID(""),
//                    Storefront.MoneyV2().setAmount("900.00")
//                        .setCurrencyCode(Storefront.CurrencyCode.EGP),
//                    quantity = 1,
//                    availableQuantity = 20,
//                    cartProduct = CartProduct(
//                        name = "Snpurdiri 60% Wired Gaming Keyboard, RGB Backlit Ultra-Compact Mini Keyboard, Waterproof Small Compact 61 Keys Keyboard for PC/Mac Gamer, Typist, Travel, Easy to Carry on Business Trip(Black-White)",
//                        collection = "Electronics",
//                        thumbnail = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMSOfds9U-FZS1k7vZ01-SA6M7MxN-esvkFAkxePEN5V4EUU1nejc1i9vMm8D274FXBQM",
//                        vendor = "Amazon"
//                    )
//                ),
//            ),
//            Storefront.MoneyV2().setAmount("303.00")
//                .setCurrencyCode(Storefront.CurrencyCode.EGP),
//            Storefront.MoneyV2().setAmount("1272.00")
//                .setCurrencyCode(Storefront.CurrencyCode.EGP),
//            null,
//            Storefront.MoneyV2().setAmount("1575.50")
//                .setCurrencyCode(Storefront.CurrencyCode.EGP),
//            checkoutPrice = Storefront.MoneyV2().setAmount("1575.50")
//                .setCurrencyCode(Storefront.CurrencyCode.EGP),
//
//        )
//        CartScreenContent(
//            cart = Cart(endCursor = this?.lineItems?.pageInfo?.endCursor ?:""),
//            itemsState = listOf(
//                CartLineState(),
//                CartLineState(isChooseQuantityOpen = true)
//            ),
//            couponState = CartCouponState(), {}, {}) {
//
//        }
//    }
//}