package com.example.shopify.feature.navigation_bar.my_account.screens.addresses.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
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
import com.example.shopify.feature.navigation_bar.cart.view.cartElevation
import com.example.shopify.feature.navigation_bar.my_account.screens.addresses.model.MyAccountMinAddress
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.theme.shopifyColors

@Composable
fun MyAccountAddressCard(
    address: MyAccountMinAddress,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(bottom = 2.dp),
        elevation = CardDefaults.elevatedCardElevation(cartElevation),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.PinDrop,
                    tint = MaterialTheme.shopifyColors.Gray,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    Modifier.clickable(role = Role.Button, onClick = onEdit),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.shopifyColors.Gray
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(
                        text = stringResource(id = R.string.edit),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.shopifyColors.Gray
                    )
                }


                if (!address.isDefault) {
                    Divider(
                        modifier = Modifier
                            .height(16.dp)
                            .padding(horizontal = 16.dp)
                            .width(1.dp)
                    )

                    Row(
                        modifier = Modifier.clickable(role = Role.Button, onClick = onDelete),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            imageVector = Icons.Rounded.DeleteOutline,
                            contentDescription = null,
                            tint = MaterialTheme.shopifyColors.Gray
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(
                            text = stringResource(id = R.string.delete),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.shopifyColors.Gray
                        )
                    }
                }

            }
            Divider(Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            TitledText(title = stringResource(id = R.string.name), text = address.name)
            TitledText(title = stringResource(id = R.string.address), text = address.address)
            TitledText(title = stringResource(id = R.string.phone), text = address.phone)
        }
    }
}

@Composable
private fun TitledText(title: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.shopifyColors.Gray
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier.weight(3f),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.W400
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyAccountAddressCard() {
    ShopifyTheme {
        MyAccountAddressCard(
            MyAccountMinAddress(
                "",
                "mohamed ibrahim",
                "kafr fagr",
                "+201120060103",
                false
            ), {}, {})
    }
}