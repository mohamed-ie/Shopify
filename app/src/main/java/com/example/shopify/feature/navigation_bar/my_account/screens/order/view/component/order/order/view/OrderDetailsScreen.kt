package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.component.OrderDetailsInfoCard
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.component.OrderDetailsProducts
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.component.PaymentMethodChooser

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderDetails(
    order: Order,
    back: () -> Unit
) {
    Scaffold(topBar = { NamedTopAppBar(back = back) }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OrderDetailsInfoCard(order = order)
            }
            items(order.lineItems) {
                OrderDetailsProducts(lineItems = it)
            }
            item {
                Text(
                    text = stringResource(id = R.string.payment_Method),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            item {
                PaymentMethodChooser(paymentStatue = order.financialStatus)
            }
            item {
                Text(
                    text = stringResource(id = R.string.order_summary),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}