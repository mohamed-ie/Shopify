package com.example.shopify.feature.address.add_address.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.common.NamedTopAppBar
import com.example.shopify.feature.common.component.ShopifyTextField
import com.example.shopify.feature.common.state.TextFieldState
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.theme.shopifyColors
import com.example.shopify.utils.shopifyLoading


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressScreenContent(
    state: AddAddressState,
    onEvent: (AddAddressEvent) -> Unit,
    back: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        NamedTopAppBar(back = back)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.location_information),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.shopifyColors.Gray,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            //street
            InputField(
                modifier = Modifier.fillMaxWidth(),
                placeholderText = stringResource(id = R.string.street),
                valueChanged = { onEvent(AddAddressEvent.StreetChanged(it)) },
                textFieldState = state.street
            )

            //apartment
            InputField(
                modifier = Modifier.fillMaxWidth(),
                placeholderText = stringResource(id = R.string.apartment),
                valueChanged = { onEvent(AddAddressEvent.ApartmentChanged(it)) },
                textFieldState = state.apartment
            )

            //city
            InputField(
                modifier = Modifier.fillMaxWidth(),
                placeholderText = stringResource(id = R.string.city),
                valueChanged = { onEvent(AddAddressEvent.CityChanged(it)) },
                textFieldState = state.city
            )

            //country
            InputField(
                modifier = Modifier.fillMaxWidth(),
                placeholderText = stringResource(id = R.string.country),
                valueChanged = { onEvent(AddAddressEvent.CountryChanged(it)) },
                textFieldState = state.country
            )

            //state
            InputField(
                modifier = Modifier.fillMaxWidth(),
                placeholderText = stringResource(id = R.string.state),
                valueChanged = { onEvent(AddAddressEvent.StateChanged(it)) },
                textFieldState = state.state
            )

            //zip
            InputField(
                modifier = Modifier.fillMaxWidth(),
                placeholderText = stringResource(id = R.string.zip),
                valueChanged = { onEvent(AddAddressEvent.ZIPChanged(it)) },
                textFieldState = state.zip
            )

            Spacer(modifier = Modifier.height(8.dp))

            //personal info
            Text(
                text = stringResource(id = R.string.personal_information),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.shopifyColors.Gray,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                //first name
                InputField(
                    modifier = Modifier.weight(1f),
                    placeholderText = stringResource(id = R.string.first_name),
                    valueChanged = { onEvent(AddAddressEvent.FirstNameChanged(it)) },
                    textFieldState = state.firstName
                )

                Spacer(modifier = Modifier.width(16.dp))

                //last name
                InputField(
                    modifier = Modifier.weight(1f),
                    placeholderText = stringResource(id = R.string.last_name),
                    valueChanged = { onEvent(AddAddressEvent.LastNameChanged(it)) },
                    textFieldState = state.lastName
                )
            }

            //phone
            InputField(
                modifier = Modifier.fillMaxWidth(),
                placeholderText = stringResource(id = R.string.phone),
                valueChanged = { onEvent(AddAddressEvent.PhoneChanged(it)) },
                textFieldState = state.phone
            )

            //
            Row(Modifier.selectableGroup()) {
                //home
                FilterChip(
                    onClick = { onEvent(AddAddressEvent.HomeAddressSelected) },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Rounded.Home,
                            tint = MaterialTheme.shopifyColors.LightGray,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.home)) },
                    selected = state.isHomeAddress
                )
                Spacer(modifier = Modifier.width(16.dp))
                //work
                FilterChip(
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Rounded.Work,
                            tint = MaterialTheme.shopifyColors.LightGray,
                            contentDescription = null
                        )
                    },
                    onClick = { onEvent(AddAddressEvent.WorkAddressSelected) },
                    label = { Text(text = stringResource(id = R.string.work)) },
                    selected = !state.isHomeAddress
                )
            }
            AnimatedVisibility(
                visible = !state.isHomeAddress,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
                //origination
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholderText = stringResource(id = R.string.origination),
                    valueChanged = { onEvent(AddAddressEvent.OrganizationChanged(it)) },
                    textFieldState = state.organization
                )
            }

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .weight(1f)
            )


            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(AddAddressEvent.Save)},
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    modifier = Modifier.shopifyLoading(state.isLoading),
                    text = stringResource(id = R.string.save)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


@Composable
private fun InputField(
    modifier: Modifier,
    placeholderText: String,
    valueChanged: (String) -> Unit,
    textFieldState: TextFieldState
) {
    ShopifyTextField(
        modifier = modifier,
        value = textFieldState.value,
        onValueChange = valueChanged,
        label = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.W400,
                color = MaterialTheme.shopifyColors.Gray
            )
        },
        isError = textFieldState.isError,
        supportingText = {
            if (textFieldState.isError)
                Text(
                    text = textFieldState.error.asString(),
                    color = MaterialTheme.colorScheme.error
                )
        }

    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAddAddressScreenContent() {
    ShopifyTheme {
        AddAddressScreenContent(
            AddAddressState(
                TextFieldState("80 Spadina Ave."),
                TextFieldState("Suite 40"),
                TextFieldState("Toronto"),
                TextFieldState("Canada"),
                TextFieldState("M5V 2J4"),
                TextFieldState("ON"),
                TextFieldState("John"),
                TextFieldState("Smith"),
                TextFieldState("1-123-456-7890"),

                ), {},{}
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewAddAddressScreenEmptyContent() {
    ShopifyTheme {
        AddAddressScreenContent(AddAddressState(), {},{})
    }
}