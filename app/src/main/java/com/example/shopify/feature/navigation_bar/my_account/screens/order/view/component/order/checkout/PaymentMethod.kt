package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout

import android.support.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.shopify.R

sealed class PaymentMethod(@StringRes val nameResource: Int, val icon: ImageVector) {
    object CreditCard :
        PaymentMethod(nameResource = R.string.credit_card, icon = Icons.Rounded.CreditCard)

    object CashOnDelivery :
        PaymentMethod(nameResource = R.string.cash_on_delivery, icon = Icons.Rounded.Payments)

    object Shopify :
        PaymentMethod(nameResource = R.string.shopify, icon = Icons.Rounded.Link)

}