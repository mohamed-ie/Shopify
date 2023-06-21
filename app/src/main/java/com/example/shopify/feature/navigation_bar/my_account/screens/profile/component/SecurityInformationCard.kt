package com.example.shopify.feature.navigation_bar.my_account.screens.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.theme.ShopifyTheme

@Composable
fun SecurityInformationCard(onChangePasswordClick: () -> Unit, onChangePhoneNumber: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        shape = RectangleShape,
//        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            //security information
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.security_information),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            //password
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.password)
            )

            Spacer(modifier = Modifier.height(8.dp))
            //
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "* * * * * * * * * * * * *",
                style = MaterialTheme.typography.titleLarge
            )
            //change password
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                onClick = onChangePasswordClick
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.change_password)
                )
            }
            Divider(modifier = Modifier.height(.5.dp))

            //change phone number
            TextButton(
                shape = RectangleShape,
                onClick = onChangePhoneNumber
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.change_phone_number)
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.this_phone_number_is_your_primary_phone_number_and_is_unique_across_shopify),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
fun PreviewSecurityInformationCard() {
    ShopifyTheme {
        SecurityInformationCard({}, {})
    }
}