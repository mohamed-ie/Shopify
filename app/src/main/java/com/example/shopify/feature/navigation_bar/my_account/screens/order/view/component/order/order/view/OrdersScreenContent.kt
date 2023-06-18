package com.example.shopify.ui.screen.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.LineItems
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.component.OrderCard
import com.example.shopify.theme.ShopifyTheme
import org.joda.time.DateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersScreenContent(
    orders: List<Order>?,
    back: () -> Unit,
    viewOrderDetails: (Int) -> Unit
) {
    Scaffold(topBar = { NamedTopAppBar(back = back) }) { innerPadding ->
        if (orders == null)
            NoOrders()
        else
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                stickyHeader {
                    ViewOrder()
                }

                itemsIndexed(orders) { index, order ->
                    OrderCard(
                        order = order,
                        viewDetails = { viewOrderDetails(index) }
                    )
                }
            }
    }
}

@Composable
@Preview(showBackground = true)
private fun NoOrders() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.no_orders), contentDescription = null)
        Text(
            text = stringResource(id = R.string.you_dont_have_any_orders_yet),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(id = R.string.what_are_you_waiting_for_start_shopping),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
    }
}

@Composable
private fun ViewOrder() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.show_orders_from),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray
        )

        Box {
            OutlinedButton(
                modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 8.dp,
                    top = 6.dp,
                    bottom = 6.dp
                ),
                shape = MaterialTheme.shapes.extraSmall,
                border = BorderStroke(width = 1.dp, color = Color.Gray.copy(alpha = .2f)),
                onClick = { /*TODO*/ }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.the_start),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
            DropdownMenu(expanded = false, onDismissRequest = { /*TODO*/ }) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.the_start)) },
                    onClick = { /*TODO*/ })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewOrderContent() {
    ShopifyTheme {
        OrdersScreenContent(
            orders = listOf(
                Order(
                    orderNumber = 85682363,
                    processedAt = DateTime("Oct 20, 2022"),
                    lineItems = listOf(
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        )
                    )
                ),
                Order(
                    orderNumber = 85682363,
                    processedAt = DateTime("Oct 20, 2022"),
                    lineItems = listOf(
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        )
                    )
                ),
                Order(
                    orderNumber = 85682363,
                    processedAt = DateTime("Oct 20, 2022"),
                    lineItems = listOf(
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        ),
                        LineItems(
                            name = "TP-LINK TL-WN725N",
                            thumbnail = "https://m.media-amazon.com/images/I/61e61vCPY6L._AC_UF894,1000_QL80_.jpg",
                            description = "TP-Link's 150Mbps wireless N Nano USB adapter, TL-WN725N allows users to connect a desktop or notebook computer to a wireless network at 150Mbps. This miniature adapter is designed to be as convenient as possible and once connected to a computer's USB port, can be left there, whether traveling or at home. It also features advanced wireless encryption and easy installation. With its miniature size and sleek design, users can connect the Nano adapter to any USB port and leave it there. There's no need to worry about blocking adjacent USB interfaces or that the adapter may fall out when moving a connected laptop from place A to B, with the tiny device flush against the USB port.",
                        )
                    )
                ),

                ),
            {}, {}
        )
    }
}