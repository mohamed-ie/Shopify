package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.theme.ShopifyTheme
import com.shopify.buy3.Storefront

@Composable
fun PaymentMethodChooser(paymentStatue: Storefront.OrderFinancialStatus) {
    Card(Modifier.padding(8.dp)) {
        Row() {
            if (paymentStatue == Storefront.OrderFinancialStatus.PENDING) {
                Icon(
                    imageVector = Icons.Default.ShoppingCartCheckout,
                    contentDescription = "cash"
                )
                Text(
                    text = stringResource(id = R.string.cash_on_delivary),
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium,
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = "credit"
                )
                Text(
                    text = stringResource(id = R.string.credit_card),
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodChooser() {
    ShopifyTheme() {
        PaymentMethodChooser(Storefront.OrderFinancialStatus.PENDING)
    }
}