package com.example.shopify.ui.screen.auth.registration

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopify.R
import com.example.shopify.ui.screen.auth.common.AuthHeader
import com.example.shopify.ui.screen.auth.common.AuthPasswordTextField
import com.example.shopify.ui.screen.auth.common.AuthTextField
import com.example.shopify.ui.screen.auth.common.AuthUIEvent
import com.example.shopify.ui.screen.auth.registration.model.SignUpUserResponseInfo
import com.example.shopify.ui.screen.auth.registration.viewModel.RegistrationViewModel
import com.example.shopify.ui.screen.order.LoadingContent


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignUpScreenEvent(
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiEvent:AuthUIEvent<SignUpUserResponseInfo>? by viewModel.uiEvent.collectAsState()

    when(uiEvent){
        is AuthUIEvent.Error -> {
            SignUpScreen(viewModel = viewModel)
        }
        AuthUIEvent.Loading -> {
            LoadingContent()
        }
        is AuthUIEvent.NavigateToHome -> {}
        null -> {
            SignUpScreen(viewModel = viewModel)
        }
    }


}

@Composable
private fun SignUpScreen(
    viewModel: RegistrationViewModel
){

    val firstName:String by viewModel.firstName.collectAsState()
    val firstNameError:Int? by viewModel.firstNameError.collectAsState()

    val lastName:String by viewModel.secondName.collectAsState()
    val secondNameError:Int? by viewModel.secondNameError.collectAsState()

    val email:String by viewModel.email.collectAsState()
    val emailError:Int? by viewModel.emailError.collectAsState()

    val phone:String by viewModel.phone.collectAsState()
    val phoneError:Int? by viewModel.phoneError.collectAsState()

    val password:String by viewModel.password.collectAsState()
    val passwordError:Int? by viewModel.passwordError.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 40.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
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

                AuthTextField(
                    text = firstName,
                    textFieldHeader = R.string.firstName,
                    placeHolder = R.string.firstName_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError = firstNameError != null,
                    errorMessage = firstNameError ?: R.string.nothing,
                    onTextChanged = { textEmailValue ->
                        viewModel.firstName.value = textEmailValue
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                AuthTextField(
                    text = lastName,
                    textFieldHeader = R.string.lastName,
                    placeHolder = R.string.lastName_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError = secondNameError  != null,
                    errorMessage = secondNameError ?: R.string.nothing,
                    onTextChanged = {textPasswordValue ->
                        viewModel.secondName.value = textPasswordValue
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                AuthTextField(
                    text = phone,
                    textFieldHeader = R.string.phone,
                    placeHolder = R.string.phone_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError = phoneError  != null,
                    errorMessage = phoneError ?: R.string.nothing,
                    onTextChanged = {textPasswordValue ->
                        viewModel.phone.value = textPasswordValue
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))
                AuthTextField(
                    text = email,
                    textFieldHeader = R.string.email,
                    placeHolder = R.string.email_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError = emailError  != null,
                    errorMessage = emailError ?: R.string.nothing,
                    onTextChanged = {textPasswordValue ->
                        viewModel.email.value = textPasswordValue
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))

                AuthPasswordTextField(
                    text = password,
                    textFieldHeader = R.string.password,
                    placeHolder = R.string.password_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError = passwordError != null,
                    errorMessage = passwordError ?: R.string.nothing,
                    onTextChanged = {textPasswordValue -> viewModel.password.value = textPasswordValue }
                )
                Spacer(modifier = Modifier.padding(vertical = 25.dp))

                Button(
                    onClick = {
                        viewModel.signUp()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(5.dp)
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
    SignUpScreenEvent()
}