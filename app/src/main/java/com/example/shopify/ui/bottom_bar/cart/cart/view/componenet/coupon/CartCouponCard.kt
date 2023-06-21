package com.example.shopify.ui.bottom_bar.cart.cart.view.componenet.coupon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.theme.Gray
import com.example.shopify.ui.theme.ShopifyTheme
import com.example.shopify.utils.shopifyLoading


@Composable
fun CartCouponCard(
    state: CartCouponState,
    applyCoupon: () -> Unit,
    clearCoupon: () -> Unit,
    couponValueChanged: (String) -> Unit
) {
    var textFieldFocused by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    Column {
        OutlinedCard(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            shape = MaterialTheme.shapes.medium,
//            elevation = CardDefaults.elevatedCardElevation(defaultElevation = cartElevation),
            border = BorderStroke(
                width = .8.dp, color = if (textFieldFocused) MaterialTheme.colorScheme.primary
                else Color.Transparent
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(modifier = Modifier
                    .onFocusChanged { textFieldFocused = it.isFocused }
                    .weight(1f),
                    value = state.coupon,
                    onValueChange = couponValueChanged,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_coupon_code),
                            color = Gray,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Light
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = state.errorVisible,
                            enter = scaleIn(),
                            exit = scaleOut()
                        ) {
                            IconButton(onClick = {
                                clearCoupon()
                                focusManager.clearFocus()
                            }) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    tint = Gray.copy(alpha = .5f),
                                    imageVector = Icons.Rounded.Cancel,
                                    contentDescription = stringResource(
                                        id = R.string.clear
                                    )
                                )
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledLabelColor = Color.Transparent,
                    )
                )
                //apply
                TextButton(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                    onClick = applyCoupon,
                    enabled = !state.isLoading
                ) {
                    Text(
                        modifier = Modifier.shopifyLoading(enabled = state.isLoading),
                        text = stringResource(id = R.string.apply),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        //error
        AnimatedVisibility(
            visible = state.errorVisible,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top),
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                text = stringResource(id = R.string.coupon_code_invalid),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCouponCard() {
    ShopifyTheme {
        CartCouponCard(CartCouponState(), {}, {}, {})
    }

}
