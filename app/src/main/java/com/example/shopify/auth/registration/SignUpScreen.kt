package com.example.shopify.auth.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.auth.common.AuthHeader
import com.example.shopify.auth.common.AuthPasswordTextField
import com.example.shopify.auth.common.AuthTextField


@Composable
fun SignUpScreen() {
    Scaffold {
        Column(
            modifier = Modifier.padding(it).padding(horizontal = 40.dp)
        ) {
            Spacer(
                modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp)
            )

            AuthHeader()

            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            Text(
                text = stringResource(R.string.create_a_shopify_account),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 3.dp)
            )

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                AuthTextField(
                    text = email,textFieldHeader = R.string.firstName,
                    placeHolder = R.string.firstName_place_holder,
                    modifier = Modifier.fillMaxWidth().padding(2.dp),
                    onTextChanged = { textEmailValue ->
                        email = textEmailValue
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                AuthTextField(
                    text = password,textFieldHeader = R.string.lastName,
                    placeHolder = R.string.lastName_place_holder,
                    modifier = Modifier.fillMaxWidth().padding(2.dp),
                    onTextChanged = {textPasswordValue ->
                        password = textPasswordValue
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                AuthTextField(
                    text = password,textFieldHeader = R.string.phone,
                    placeHolder = R.string.phone_place_holder,
                    modifier = Modifier.fillMaxWidth().padding(2.dp),
                    onTextChanged = {textPasswordValue ->
                        password = textPasswordValue
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))
                AuthTextField(
                    text = password,textFieldHeader = R.string.email,
                    placeHolder = R.string.email_place_holder,
                    modifier = Modifier.fillMaxWidth().padding(2.dp),
                    onTextChanged = {textPasswordValue ->
                        password = textPasswordValue
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                AuthPasswordTextField(
                    text = password,textFieldHeader = R.string.password,
                    placeHolder = R.string.password_place_holder,
                    modifier = Modifier.fillMaxWidth().padding(2.dp),
                    onTextChanged = {textPasswordValue -> password = textPasswordValue }
                )
                Spacer(modifier = Modifier.padding(vertical = 25.dp))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(5.dp),
                    enabled = false
                ) {
                    Text(text = stringResource(R.string.signin))
                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Row(
                    modifier = Modifier.padding(bottom = 50.dp)
                ){
                    Text(
                        text = stringResource(R.string.already_have_an_account),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray)

                    Text(
                        text = stringResource(R.string.signin),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black, modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewSignUpScreen() {
    SignUpScreen()
}