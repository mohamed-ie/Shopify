package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.theme.ShopifyTheme

@Composable
fun PaymentMethodScreen() {
    var selectedOption by remember { mutableStateOf(PaymentMethod.CreditCard) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Payment Method",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        RadioButtonOption(
            text = "Credit Card",
            selected = selectedOption == PaymentMethod.CreditCard,
            onClick = { selectedOption = PaymentMethod.CreditCard }
        )

        RadioButtonOption(
            text = "Cash on Delivery",
            selected = selectedOption == PaymentMethod.CashOnDelivery,
            onClick = { selectedOption = PaymentMethod.CashOnDelivery }
        )
    }
}

@Composable
fun RadioButtonOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        RadioButton(
            selected = selected,
            onClick = { onClick() },
            colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
        )
        Text(
            text = text,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodScreen() {
    ShopifyTheme {
        PaymentMethodScreen()
    }
}