package com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.Graph
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view.component.MyAccountSignedInHeader
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view.component.SettingItem
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.theme.shopifyColors

@Composable
fun MyAccountSignedInScreenContent(
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
            MyAccountSignedInHeader(
                name = state.name,
                mail = state.email,
                navigateTo = navigateTo
            )

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
                Divider(modifier = Modifier.padding(horizontal = 16.dp))

                SettingItem(
                    text = stringResource(id = R.string.addresses),
                    icon = Icons.Outlined.PinDrop,
                    onClick = { navigateTo(Graph.ADDRESS) }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    role = Role.Button,
                    onClick = { onEvent(MyAccountEvent.ToggleSignOutConfirmDialogVisibility) })
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Rounded.PowerSettingsNew,
                tint = MaterialTheme.shopifyColors.Black,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = stringResource(id = R.string.sign_out),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.shopifyColors.Black
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMyAccountSignedInScreenContent() {
    ShopifyTheme {
        MyAccountSignedInScreenContent(
            MyAccountState("mohamed", "mohammedie98@gmail.com", "EGP"),
            PaddingValues(),
            {}
        ) { }
    }
}