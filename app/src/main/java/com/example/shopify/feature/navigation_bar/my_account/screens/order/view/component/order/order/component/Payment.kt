package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.PaymentMethod
import com.example.shopify.theme.ShopifyTheme
import com.shopify.buy3.Storefront

@Composable
fun PaymentMethodChooser(paymentStatue: Storefront.OrderFinancialStatus) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(35.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        if (paymentStatue != Storefront.OrderFinancialStatus.PAID) {
            PaymentElement(payMethod = PaymentMethod.CashOnDelivery)
        } else {
            PaymentElement(payMethod = PaymentMethod.CreditCard)
        }
    }
}

@Composable
fun PaymentElement(payMethod: PaymentMethod) {
    Row(modifier = Modifier.padding(6.dp)) {
        Icon(
            imageVector = payMethod.icon,
            contentDescription = "credit",
            Modifier.padding(end = 8.dp)
        )
        Text(
            text = stringResource(id = payMethod.nameResource),
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodChooser() {
    ShopifyTheme() {
        PaymentMethodChooser(Storefront.OrderFinancialStatus.PAID)
    }
}