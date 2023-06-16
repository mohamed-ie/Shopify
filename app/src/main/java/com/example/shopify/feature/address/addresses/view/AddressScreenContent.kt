package com.example.shopify.feature.address.addresses.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.address.addresses.model.MyAccountMinAddress
import com.example.shopify.feature.address.addresses.view.component.MyAccountAddressCard
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.my_account.MyAccountGraph
import com.shopify.graphql.support.ID

@Composable
fun AddressesScreenContent(
    back: () -> Unit,
    allowPick: Boolean,
    onEvent: (AddressesEvent) -> Unit,
    navigateTo: (String) -> Unit,
    addresses: List<MyAccountMinAddress>
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
                items(addresses) { address ->
                    MyAccountAddressCard(
                        address = address,
                        clickable = allowPick,
                        onClick = {
                            onEvent(AddressesEvent.UpdateCartAddress(address.id))
                        },
                        onDelete = {
                            onEvent(
                                AddressesEvent.ToggleDeleteConfirmationDialogVisibility(address.id)
                            )
                        },
                        onEdit = {
                            /*   navigateTo(address.id)*/
                        }
                    )
                }
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClick = { navigateTo(MyAccountGraph.ADD_ADDRESS) },
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

@Preview
@Composable
fun PreviewAddressesScreenContent() {
    AddressesScreenContent({}, false, {}, {}, listOf(
        MyAccountMinAddress(
            ID(""),
            "",
            "",
            "",
            false
        ),
        MyAccountMinAddress(
            ID(""),
            "",
            "",
            "",
            true
        )
    )
    )
}