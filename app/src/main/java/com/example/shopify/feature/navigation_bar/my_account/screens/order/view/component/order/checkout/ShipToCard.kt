package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.theme.ShopifyTheme

@Composable
fun ShipToCard(
    address: String,
    phoneNumber: String,
    onChangeClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ship To",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            ClickableText(
                text = AnnotatedString("Change"),
                style = TextStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                ),
                onClick = { offset ->
                    if (offset == 0) {
                        onChangeClick()
                    }
                }
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "location",
                    modifier = Modifier
                        .padding(
                            start = 4.dp, end = 16.dp
                        )
                        .align(Alignment.CenterVertically)
                )
                Column() {
                    Text(
                        text = "home",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Text(
                        text = address,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = phoneNumber,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShipToScreen() {
    ShopifyTheme {
        ShipToCard(
            address = "galal street - elhassan building - haram - giza - egypt ",
            phoneNumber = "+201117513385"
        ) {

        }
    }
}