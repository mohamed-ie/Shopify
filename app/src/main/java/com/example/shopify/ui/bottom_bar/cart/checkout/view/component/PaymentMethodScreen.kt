package com.example.shopify.ui.bottom_bar.cart.checkout.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.cart.checkout.view.state.PaymentMethod
import com.example.shopify.ui.theme.ShopifyTheme

private val paymentMethods = listOf(
    PaymentMethod.Shopify,
    PaymentMethod.CreditCard,
    PaymentMethod.CashOnDelivery
)

@Composable
fun PaymentMethodScreen(
    selected: PaymentMethod,
    paymentMethodChanged: (PaymentMethod) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Payment Method",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        paymentMethods.forEach { method ->
            RadioButtonOption(
                text = stringResource(id = method.nameResource),
                icon = method.icon,
                selected = selected == method,
                onClick = { paymentMethodChanged(method) }
            )
        }
    }
}

@Composable
fun RadioButtonOption(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                border = BorderStroke(1.dp, color = Color.LightGray),
                shape = MaterialTheme.shapes.medium
            )
            .clip(shape = MaterialTheme.shapes.medium)
            .background(Color.White)
            .clickable(onClick = onClick)

    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (icon == Icons.Rounded.CreditCard)
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.stripe_wordmark___blurple),
                contentDescription = null
            )
        else Icon(
            modifier = Modifier.padding(horizontal = 8.dp),
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodScreen() {
    ShopifyTheme {
        PaymentMethodScreen(PaymentMethod.CreditCard, {})
    }
}