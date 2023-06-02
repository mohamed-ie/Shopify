package com.example.shopify.ui.screen.auth.login.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.shopify.ui.screen.auth.common.AuthHeader
import com.example.shopify.ui.screen.auth.common.AuthPasswordTextField
import com.example.shopify.ui.screen.auth.common.AuthTextField


@Composable
fun LoginScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 40.dp)
        ) {

            Spacer(
                modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp)
            )

            AuthHeader()

            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            Text(
                text = stringResource(id = R.string.login_welcome_expression),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 3.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            AuthTextField(
                text = email,
                textFieldHeader = R.string.email,
                placeHolder = R.string.email_place_holder,
                modifier = Modifier.fillMaxWidth().padding(2.dp),
                isError = false,
                errorMessage = 0,
                onTextChanged = { textEmailValue -> email = textEmailValue }
            )

            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            AuthPasswordTextField(
                text = password,
                textFieldHeader = R.string.password,
                placeHolder = R.string.password_place_holder,
                modifier = Modifier.fillMaxWidth().padding(2.dp),
                isError = false,
                errorMessage = 0,
                onTextChanged = {textPasswordValue ->
                    password = textPasswordValue
                }
            )

            Spacer(modifier = Modifier.padding(vertical = 25.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                shape = RoundedCornerShape(5.dp),
                enabled = false
            ){
                Text(text = stringResource(R.string.signin))
            }

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Row{
                Text(
                    text = stringResource(R.string.dont_have_an_account_text),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )

                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewLogin() {
    LoginScreen()
}

