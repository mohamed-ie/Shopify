package com.example.shopify.ui.address.addresses.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.theme.shopifyColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAccountAddressCard(
    phone: String,
    address: String,
    name: String,
    isDefault: Boolean,
    clickable: Boolean,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(bottom = 2.dp),
        elevation = CardDefaults.elevatedCardElevation(.5.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(Color.White),
        onClick = if (clickable) onClick else ({ })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Row(
                    Modifier
                        .clickable(role = Role.Button, onClick = onEdit)
                        .padding(horizontal = 4.dp)
                        .padding(top=4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
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


                if (isDefault) {
                    Divider(
                        modifier = Modifier
                            .height(16.dp)
                            .padding(horizontal = 16.dp)
                            .width(1.dp)
                    )

                    Row(
                        Modifier
                            .clickable(role = Role.Button, onClick = onEdit)
                            .padding(horizontal = 4.dp)
                            .padding(top=4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Outlined.DeleteOutline,
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
            TitledText(title = stringResource(id = R.string.name), text = name)
            TitledText(title = stringResource(id = R.string.address), text = address)
            TitledText(title = stringResource(id = R.string.phone), text = phone)
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
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewMyAccountAddressCard() {
//    ShopifyTheme {
//        MyAccountAddressCard(
//            MyAccountMinAddress(
//                ID(""),
//                "mohamed ibrahim",
//                "kafr fagr",
//                "+201120060103",
//                false
//            ),false, {}, {}
//        ) {
//
//        }
//    }
//}