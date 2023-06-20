package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.order.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.LineItems
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.OrderItemState
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.ui.screen.order.component.OrdersFilledTonalButton
import com.shopify.buy3.Storefront.OrderFulfillmentStatus
import org.joda.time.DateTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderCard(
    order: Order,
    viewDetails: () -> Unit,
    addReview:(Int)->Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                //order id
                Text(
                    text = "${stringResource(id = R.string.order_id)}  ${
                        order.orderNumber.toString()
                    }",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(2.dp))
                //order date
                Text(
                    text = "${stringResource(id = R.string.placed_on_date)}  ${
                        order.processedAt.toString(
                            "MMMM dd, yyyy"
                        )
                    }",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            }
            //view details
            TextButton(onClick = viewDetails) {
                Text(text = stringResource(id = R.string.view_details))
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }

        Divider(modifier = Modifier.padding(horizontal = 16.dp))

        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(order.lineItems.count()) { orderIndex ->
                OrderItemContent(
                    orderItem = order.lineItems[orderIndex],
                    fulfillmentStatus = order.fulfillment,
                    addReview = {addReview(orderIndex)}
                )
            }
        }


    }
}


@Composable
private fun OrderItemContent(
    orderItem: LineItems,
    fulfillmentStatus: OrderItemState,
    addReview:()->Unit
) {
   Column {
       Row(
           modifier = Modifier
               .padding(start = 24.dp, top = 16.dp)
               .padding(vertical = 8.dp)
               .width(300.dp)
       ) {
           SubcomposeAsyncImage(
               modifier = Modifier
                   .height(80.dp)
                   .aspectRatio(1f),
               contentScale = ContentScale.Crop,
               model = orderItem.thumbnail,
               contentDescription = null,
               loading = {
                   Box(
                       Modifier
                           .fillMaxSize()
                   )
               },
               error = {
                   Icon(
                       modifier = Modifier.fillMaxSize(),
                       imageVector = Icons.Rounded.BrokenImage,
                       tint = Color.Gray,
                       contentDescription = null
                   )
               }
           )
           Spacer(modifier = Modifier.width(16.dp))
           Column {
               //name
               Text(
                   text = orderItem.name,
                   style = MaterialTheme.typography.bodyMedium,
                   maxLines = 1,
                   overflow = TextOverflow.Ellipsis
               )
               Spacer(modifier = Modifier.height(4.dp))
               //description
               Text(
                   text = orderItem.description,
                   style = MaterialTheme.typography.bodyMedium,
                   maxLines = 2,
                   overflow = TextOverflow.Ellipsis
               )
               Spacer(modifier = Modifier.height(16.dp))
               //status
               Text(
                   text = fulfillmentStatus.state,
                   style = MaterialTheme.typography.labelMedium,
                   color = fulfillmentStatus.color()
               )
           }
       }
       if(fulfillmentStatus != OrderItemState.Delivered()){
           OrdersFilledTonalButton(
               text = stringResource(id = R.string.review_product),
               onClick = addReview
           )
       }
   }
}

@Preview
@Composable
fun PreviewOrderCard() {
    ShopifyTheme {
        OrderCard(
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
            {}
        ) {}
    }
}
