package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.common.component.ShopifyOutlinedButton
import com.example.shopify.feature.common.component.ShopifyOutlinedButtonState
import com.example.shopify.theme.shopifyColors
import com.example.shopify.utils.shopifyLoading

@Composable
fun ProductSnackBar(
    opened: Boolean,
    selected: Int,
    availableQuantity: Int,
    isChangingQuantity: Boolean,
    quantitySelected: (Int) -> Unit,
    openQuantity:()->Unit,
    closeQuantity:()->Unit,
    addToCard:() ->Unit
) {
    Column(modifier = Modifier.background(Color.White)) {
        Divider(color = MaterialTheme.shopifyColors.ServerColor, thickness = 1.dp)
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier.padding(start = 25.dp)
        ) {
            ProductQuantitySelector(
                opened = opened,
                selected = selected,
                availableQuantity = availableQuantity,
                isChangingQuantity = isChangingQuantity,
                quantitySelected = quantitySelected,
                closeQuantity = closeQuantity
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .fillMaxWidth()

        ) {
            ShopifyOutlinedButton(
                onClick = {openQuantity() },
                modifier = Modifier.height(45.dp)
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ){
                    Text(
                        text = stringResource(R.string.qty),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    Text(
                        text = selected.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { addToCard() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                shape = RoundedCornerShape(7.dp)
            ) {

                Text(
                    text = stringResource(R.string.add_to_cart),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

}

@Composable
private fun ProductQuantitySelector(
    opened: Boolean,
    selected: Int,
    availableQuantity: Int,
    isChangingQuantity: Boolean,
    quantitySelected: (Int) -> Unit,
    closeQuantity:()->Unit
) {
    val quantityListState = rememberLazyListState()
    LaunchedEffect(key1 = Unit, block = {
        quantityListState.scrollToItem(selected + 1)
    })
    AnimatedVisibility(
        visible = opened,
        enter = expandVertically(expandFrom = Alignment.Bottom),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom),
    ) {
        Column(
            modifier = Modifier.padding(end = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Quantity",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { closeQuantity() },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        tint = Color.DarkGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(state = quantityListState, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(availableQuantity) {
                    ShopifyOutlinedButton(
                        shape = RoundedCornerShape(3.dp),
                        onClick = { quantitySelected(it + 1) },
                        state = if ((it + 1) == selected)
                            ShopifyOutlinedButtonState.Selected
                        else
                            ShopifyOutlinedButtonState.Normal
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .shopifyLoading(enabled = isChangingQuantity),
                            text = "${it + 1}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}