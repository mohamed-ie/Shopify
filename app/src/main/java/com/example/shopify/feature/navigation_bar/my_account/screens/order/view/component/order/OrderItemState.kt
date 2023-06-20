package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.shopify.theme.shopifyColors


sealed class OrderItemState(val state:String,val color:@Composable ()-> Color) {
    class Progress : OrderItemState("In-Progress", { MaterialTheme.colorScheme.primary })
    class Delivered : OrderItemState("Delivered", { MaterialTheme.shopifyColors.DarkGreenColor})
    class Canceled : OrderItemState("Canceled", { Color.Red})
}
