package com.example.shopify.feature.navigation_bar.cart.view.componenet.cart_item_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.cart.model.CartLine
import com.example.shopify.feature.navigation_bar.common.component.ShopifyOutlinedButton
import com.example.shopify.feature.navigation_bar.common.component.ShopifyOutlinedButtonState
import com.example.shopify.theme.Gray
import com.example.shopify.utils.shopifyLoading
import kotlinx.coroutines.delay

@Composable
fun CartItemCard(
    state: CartLineState,
    cartLine: CartLine,
    toggleQuantitySelectorVisibility: () -> Unit,
    removeFromCart: () -> Unit,
    moveToWishlist: () -> Unit,
    quantitySelected: (Int) -> Unit,
    onClick:()->Unit
) {
    val product = cartLine.cartProduct
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .background(Color.White)
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .aspectRatio(1f, true)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                model = product.thumbnail,
                contentDescription = null,
                loading = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .shopifyLoading()
                    )
                },
                error = {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Rounded.BrokenImage,
                        tint = Color.Gray,
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                //collection
                Text(
                    text = product.collection,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

                //product name
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                //price
                Text(
                    text = cartLine.price?:"",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                //sold by
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.sold_by))
                        append(" ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append(product.vendor)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            //quantity selected
            ShopifyOutlinedButton(
                onClick = toggleQuantitySelectorVisibility,
                state = if (state.isChooseQuantityOpen)
                    ShopifyOutlinedButtonState.Active
                else
                    ShopifyOutlinedButtonState.Normal
            ) {
                Text(
                    modifier = Modifier.shopifyLoading(state.isChangingQuantity),
                    text = "${cartLine.quantity}",
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

                val angle by animateFloatAsState(targetValue = if (state.isChooseQuantityOpen) 0f else 180f)

                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(angle),
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    tint = Gray,
                    contentDescription = stringResource(id = R.string.choose_quantity_closed)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // remove
            ShopifyOutlinedButton(onClick = removeFromCart) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Rounded.DeleteOutline,
                    tint = Gray,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    modifier = Modifier.shopifyLoading(enabled = state.isRemoving),
                    text = stringResource(id = R.string.remove),
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            //add to wishlist
            ShopifyOutlinedButton(onClick = moveToWishlist) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Rounded.FavoriteBorder,
                    tint = Gray,
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

                Text(
                    modifier = Modifier.shopifyLoading(enabled = state.isMovingToWishlist),
                    text = stringResource(id = R.string.move_to_wishlist),
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray
                )
            }
        }


        QuantitySelector(
            opened = state.isChooseQuantityOpen,
            selected = cartLine.quantity,
            availableQuantity = cartLine.availableQuantity,
            quantitySelected = quantitySelected
        )

        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun QuantitySelector(
    opened: Boolean,
    selected: Int,
    availableQuantity: Int,
    quantitySelected: (Int) -> Unit
) {
    val quantityListState = rememberLazyListState()
    AnimatedVisibility(
        visible = opened,
        enter = expandVertically(expandFrom = Alignment.Bottom),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom),
    ) {
        LaunchedEffect(key1 = Unit, block = {
            delay(800)
            quantityListState.animateScrollToItem(selected - 1)
        })

        Column {
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(state = quantityListState, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(availableQuantity) {
                    ShopifyOutlinedButton(
                        onClick = { quantitySelected(it+1) },
                        state = if ((it) == selected-1)
                            ShopifyOutlinedButtonState.Selected
                        else
                            ShopifyOutlinedButtonState.Normal
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 4.dp),
                            text = "${it+1}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCartCard() {
//    ShopifyTheme {
//        CartItemCard(
//            CartLineState(),
//            CartLine(
//                productVariantID = ID(""),
//                id = ID(""),
//                Storefront.MoneyV2().setAmount("372.00")
//                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
//                quantity = 1,
//                availableQuantity = 5,
//                cartProduct = CartProduct(
//                    name = "Pro Airpods Compatible With Android iPhone White",
//                    collection = "Generic",
//                    thumbnail = "https://m.media-amazon.com/images/I/51ujve2qY8L._AC_SY741_.jpg",
//                    vendor = "Egyptian German"
//                )
//            ),
//            {},
//            {},
//            {},
//            {},
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCartCardOpenQuantityChooser() {
//    ShopifyTheme {
//        CartItemCard(
//            CartLineState(true, ),
//            CartLine(
//                productVariantID = ID(""),
//                id = ID(""),
//                Storefront.MoneyV2().setAmount("372.00")
//                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
//                quantity = 5,
//                availableQuantity = 10,
//                cartProduct = CartProduct(
//                    name = "Pro Airpods Compatible With Android iPhone White",
//                    collection = "Generic",
//                    thumbnail = "https://m.media-amazon.com/images/I/51ujve2qY8L._AC_SY741_.jpg",
//                    vendor = "Egyptian German"
//                )
//            ),
//            {},
//            {},
//            {},
//            {},
//        )
//    }
//}