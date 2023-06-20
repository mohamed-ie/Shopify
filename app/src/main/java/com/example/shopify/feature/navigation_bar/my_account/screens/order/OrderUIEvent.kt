package com.example.shopify.feature.navigation_bar.my_account.screens.order

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.shopify.theme.shopifyColors

sealed interface OrderUIEvent {
    object NavigateToOrdersScreen : OrderUIEvent
    object NavigateToCreditCardInfoScreen : OrderUIEvent
}


sealed interface ReviewUIEvent {
    class SendTitle(val value:String) : ReviewUIEvent
    class SendContent(val value:String) : ReviewUIEvent
    object Submit : ReviewUIEvent
    object Close : ReviewUIEvent
    class View(val orderIndex:Int,val lineIndex:Int) : ReviewUIEvent
    class SendRate(val value:Int) : ReviewUIEvent
}

