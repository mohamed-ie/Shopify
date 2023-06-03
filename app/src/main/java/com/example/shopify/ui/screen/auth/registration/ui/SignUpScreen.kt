package com.example.shopify.ui.screen.auth.registration.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.example.shopify.ui.screen.auth.common.ErrorAuthUiState
import com.example.shopify.ui.screen.auth.common.ErrorCard
import com.example.shopify.ui.screen.auth.common.RegistrationUiState
import com.example.shopify.ui.screen.auth.registration.viewModel.RegistrationViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun SignUpScreen(
    viewModel: RegistrationViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val errorUiState by viewModel.uiErrorState.collectAsState()

    LaunchedEffect(key1 = Unit){
        viewModel.uiEvent
            .onEach {authUIEvent ->
                when(authUIEvent){
                    is AuthUIEvent.Loading -> {}
                    is AuthUIEvent.NavigateToHome -> {}
                }
            }
            .launchIn(this)
    }
    SignUpScreenContent(
        uiState = uiState,
        errorUiState = errorUiState,
        onFirstNameTextChange = viewModel::sendFirstNameValue,
        onSecondNameTextChange = viewModel::sendSecondNameValue,
        onEmailTextChange = viewModel::sendEmailValue,
        onPhoneTextChange = viewModel::sendPhoneValue,
        onPasswordTextChange = viewModel::sendPasswordValue,
        onSignUp = viewModel::signUp
    )
}

@Composable
private fun SignUpScreenContent(
    uiState: RegistrationUiState,
    errorUiState: ErrorAuthUiState,
    onFirstNameTextChange:(String) -> Unit,
    onSecondNameTextChange:(String) -> Unit,
    onEmailTextChange:(String) -> Unit,
    onPhoneTextChange:(String) -> Unit,
    onPasswordTextChange:(String) -> Unit,
    onSignUp:() ->Unit
){
    Column {
        AnimatedVisibility(
            visible = errorUiState.isError,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top, animationSpec = tween(1500)),
        ) {
            ErrorCard(error = errorUiState.error)
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 40.dp)
        ) {
            Spacer(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
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

            ) {

                Spacer(modifier = Modifier.padding(vertical = 12.dp))

                AuthTextField(
                    text = uiState.firstName.value,
                    textFieldHeader = R.string.firstName,
                    placeHolder = R.string.firstName_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError = uiState.firstName.error != null,
                    errorMessage = uiState.firstName.error ?: R.string.nothing,
                    onTextChanged = { textFirstNameValue ->
                        onFirstNameTextChange(textFirstNameValue)
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 7.dp))

                AuthTextField(
                    text = uiState.secondName.value,
                    textFieldHeader = R.string.lastName,
                    placeHolder = R.string.lastName_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError = uiState.secondName.error  != null,
                    errorMessage = uiState.secondName.error ?: R.string.nothing,
                    onTextChanged = {textSecondValue ->
                        onSecondNameTextChange(textSecondValue)
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 7.dp))

                AuthTextField(
                    text = uiState.phone.value,
                    textFieldHeader = R.string.phone,
                    placeHolder = R.string.phone_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError = uiState.phone.error  != null,
                    errorMessage = uiState.phone.error ?: R.string.nothing,
                    onTextChanged = {textPhoneValue ->
                        onPhoneTextChange(textPhoneValue)
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 7.dp))
                AuthTextField(
                    text = uiState.email.value,
                    textFieldHeader = R.string.email,
                    placeHolder = R.string.email_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError =  uiState.email.error  != null,
                    errorMessage =  uiState.email.error ?: R.string.nothing,
                    onTextChanged = {textEmailValue ->
                        onEmailTextChange(textEmailValue)
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 7.dp))

                AuthPasswordTextField(
                    text = uiState.password.value,
                    textFieldHeader = R.string.password,
                    placeHolder = R.string.password_place_holder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    isError =  uiState.password.error != null,
                    errorMessage =  uiState.password.error ?: R.string.nothing,
                    onTextChanged = {textPasswordValue ->  onPasswordTextChange(textPasswordValue)}
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Button(
                    onClick = {
                        onSignUp()
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = stringResource(R.string.signin))
                }

                Spacer(modifier = Modifier.padding(vertical = 12.dp))

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
    SignUpScreen(hiltViewModel())
}