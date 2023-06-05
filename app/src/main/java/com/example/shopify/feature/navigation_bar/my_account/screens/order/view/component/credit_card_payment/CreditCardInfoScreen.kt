package com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.common.component.ShopifyTextField
import com.example.shopify.feature.common.state.TextFieldState
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.theme.shopifyColors

@Composable
fun CreditCardInfoScreen(
    state: CreditCardInfoState,
    onEvent: (CreditCardInfoEvent) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.card_information),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.we_accept),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.height(24.dp),
                painter = painterResource(id = R.drawable.visa_logo),
                contentDescription = stringResource(id = R.string.visa)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier.height(24.dp),
                painter = painterResource(id = R.drawable.mastercard_logo),
                contentDescription = stringResource(id = R.string.mastercard)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            InputField(
                modifier = Modifier.weight(1f),
                placeholderText = stringResource(id = R.string.first_name),
                labelText = stringResource(id = R.string.enter_first_name),
                valueChanged = { onEvent(CreditCardInfoEvent.FirstNameChanged(it)) },
                textFieldState = state.firstNameState
            )

            Spacer(modifier = Modifier.width(16.dp))

            InputField(
                modifier = Modifier.weight(1f),
                placeholderText = stringResource(id = R.string.last_name),
                labelText = stringResource(id = R.string.enter_first_name),
                valueChanged = { onEvent(CreditCardInfoEvent.LastNameChanged(it)) },
                textFieldState = state.lastNameState
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        InputField(
            modifier = Modifier.fillMaxWidth(),
            placeholderText = stringResource(id = R.string.enter_your_card_number),
            labelText = stringResource(id = R.string.card_number),
            valueChanged = { onEvent(CreditCardInfoEvent.CardNumberChanged(it)) },
            textFieldState = state.cardNumberState
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            InputField(
                modifier = Modifier.weight(1f),
                placeholderText = stringResource(id = R.string.mm),
                labelText = stringResource(id = R.string.expire_month),
                valueChanged = { onEvent(CreditCardInfoEvent.ExpireMonthChanged(it)) },
                textFieldState = state.expireMonthState
            )
            Spacer(modifier = Modifier.width(16.dp))

            InputField(
                modifier = Modifier.weight(1f),
                placeholderText = stringResource(id = R.string.yy),
                labelText = stringResource(id = R.string.expire_year),
                valueChanged = { onEvent(CreditCardInfoEvent.ExpireYearChanged(it)) },
                textFieldState = state.expireYearState
            )
            Spacer(modifier = Modifier.width(16.dp))
            InputField(
                modifier = Modifier.weight(1f),
                placeholderText = stringResource(id = R.string.secure_code),
                labelText = stringResource(id = R.string.cvv),
                valueChanged = { onEvent(CreditCardInfoEvent.CCVChanged(it)) },
                textFieldState = state.ccvState
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(CreditCardInfoEvent.Checkout) },
            shape = MaterialTheme.shapes.small
        ) {
            Text(text = stringResource(id = R.string.checkout))
        }
    }
}

@Composable
private fun InputField(
    modifier: Modifier,
    placeholderText: String,
    labelText: String,
    valueChanged: (String) -> Unit,
    textFieldState: TextFieldState
) {
    ShopifyTextField(
        modifier = modifier,
        value = textFieldState.value,
        onValueChange = valueChanged,
        placeholder = {
            Text(
                text = placeholderText,
                color = MaterialTheme.shopifyColors.LightGray,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )
        },
        label = {
            Text(
                text = labelText,
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
fun PreviewCreditCardInfoScreen() {
    ShopifyTheme {
        CreditCardInfoScreen(CreditCardInfoState(), {})
    }
}