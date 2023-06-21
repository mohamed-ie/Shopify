package com.example.shopify.ui.bottom_bar.my_account.my_account.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.my_account.my_account.view.component.MyAccountHeader
import com.example.shopify.ui.bottom_bar.my_account.my_account.view.component.SettingItem
import com.example.shopify.ui.theme.ShopifyTheme
import com.example.shopify.ui.theme.shopifyColors

@Composable
fun MyAccountScreenContent(
    state: MyAccountState,
    innerPadding: PaddingValues,
    navigateTo: (String) -> Unit,
    onEvent: (MyAccountEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {

        MyAccountHeader(navigateTo = navigateTo)

        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.settings),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.shopifyColors.Gray
        )

        Card(
            modifier = Modifier.padding(vertical = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RectangleShape
        ) {
            Column {
                SettingItem(
                    text = stringResource(id = R.string.currency),
                    icon = Icons.Rounded.CurrencyExchange,
                    current = { Text(text = state.currency) },
                    onClick = { onEvent(MyAccountEvent.ToggleCurrencyRadioGroupModalSheetVisibility) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyAccountScreenContent() {
    ShopifyTheme {
        MyAccountScreenContent(
            MyAccountState("mohamed", "mohammedie98@gmail.com", "EGP"),
            PaddingValues(),
            {}
        ) { }
    }
}