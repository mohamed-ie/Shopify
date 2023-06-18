package com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Ballot
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.Graph
import com.example.shopify.feature.auth.Auth
import com.example.shopify.theme.ShopifyTheme

@Composable
fun MyAccountSignedInHeader(
    name: String,
    mail: String,
    navigateTo: (String) -> Unit
) {
    Card(
        modifier = Modifier.padding(bottom = 2.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RectangleShape
    ) {
        Column(
            Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.ahlan_user, name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = mail
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MyHeaderListItem(
                    name = stringResource(id = R.string.profile),
                    icon = Icons.Outlined.AccountCircle,
                    onClick = {/*navigateTo()*/ }
                )

                MyHeaderListItem(
                    name = stringResource(id = R.string.orders),
                    icon = Icons.Outlined.Ballot,
                    onClick = { navigateTo(Graph.ORDERS) }
                )

                MyHeaderListItem(
                    name = stringResource(id = R.string.wishlist),
                    icon = Icons.Rounded.Favorite,
                    onClick = { navigateTo(Graph.WISH_LIST) }
                )

            }
        }
    }
}


@Composable
fun MyAccountHeader(navigateTo: (String) -> Unit) {
    Card(
        modifier = Modifier.padding(bottom = 2.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RectangleShape
    ) {
        Column(
            Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.ahlan_nice_to_meet_you),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.the_regions_favorite_online_market)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MyHeaderListItem(
                    name = stringResource(id = R.string.signin),
                    icon = Icons.Outlined.AccountCircle,
                    onClick = { navigateTo(Auth.SIGN_IN) }
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = .7f))
                            .padding(4.dp),
                        onClick = {navigateTo(Auth.SIGN_UP) }
                    ) {
                        Box(
                            modifier = Modifier.size(33.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = Icons.Outlined.AccountCircle,
                                tint = Color.Yellow.copy(alpha = .8f),
                                contentDescription = null
                            )
                            Icon(
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.TopEnd),
                                imageVector = Icons.Outlined.AddCircle,
                                tint = Color.Yellow.copy(alpha = .8f),
                                contentDescription = null
                            )

                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}


@Composable
private fun MyHeaderListItem(name: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = .7f))
                .padding(4.dp),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = icon,
                tint = Color.Yellow.copy(alpha = .8f),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyAccountHeader() {
    ShopifyTheme {
        Column {
            MyAccountSignedInHeader(name = "Mohamed", "mohammedie98@gmail.com", {})
            MyAccountHeader({})
        }
    }
}