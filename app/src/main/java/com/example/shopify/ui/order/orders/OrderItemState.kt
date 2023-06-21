package com.example.shopify.ui.order.orders

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.shopify.ui.theme.shopifyColors


sealed class OrderItemState(val state:String,val color:@Composable ()-> Color) {
    class Progress : OrderItemState("In-Progress", { MaterialTheme.colorScheme.primary })
    class Delivered : OrderItemState("Delivered", { MaterialTheme.shopifyColors.DarkGreenColor})
    class Canceled : OrderItemState("Canceled", { Color.Red})
}
