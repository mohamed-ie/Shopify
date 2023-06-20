package com.example.shopify.feature.address.addresses.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.address.AddressGraph
import com.example.shopify.feature.address.addresses.view.component.MyAccountAddressCard
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.shopify.buy3.Storefront.MailingAddress

@Composable
fun AddressesScreenContent(
    back: () -> Unit,
    allowPick: Boolean,
    onEvent: (AddressesEvent) -> Unit,
    navigateTo: (String) -> Unit,
    addresses: List<MailingAddress>
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { NamedTopAppBar(back = back) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(addresses) { index, address ->
                    Spacer(modifier = Modifier.height(8.dp))
                    MyAccountAddressCard(
                        address = address.formattedArea,
                        phone = address.phone,
                        name = "${address.firstName} ${address.lastName}",
                        clickable = allowPick,
                        onClick = {
                            onEvent(AddressesEvent.UpdateAddress(index))
                        },
                        onDelete = {
                            onEvent(
                                AddressesEvent.ToggleDeleteConfirmationDialogVisibility(index)
                            )
                        },
                        onEdit = {
                            /*   navigateTo(address.index)*/
                        },
                        isDefault = index != 0
                    )
                }
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClick = { navigateTo(AddressGraph.ADD_ADDRESS) },
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(id = R.string.add_new_address)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
//
//@Preview
//@Composable
//fun PreviewAddressesScreenContent() {
//    AddressesScreenContent({}, false, {}, {}, listOf(
//        MyAccountMinAddress(
//            ID(""),
//            "",
//            "",
//            "",
//            false
//        ),
//        MyAccountMinAddress(
//            ID(""),
//            "",
//            "",
//            "",
//            true
//        )
//    )
//    )
//}