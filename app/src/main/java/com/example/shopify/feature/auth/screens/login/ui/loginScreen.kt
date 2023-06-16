package com.example.shopify.feature.auth.screens.login.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.auth.screens.common.components.AuthHeader
import com.example.shopify.feature.auth.screens.common.components.AuthPasswordTextField
import com.example.shopify.feature.auth.screens.common.components.AuthTextField
import com.example.shopify.feature.auth.screens.common.AuthUIEvent
import com.example.shopify.feature.auth.screens.common.ErrorAuthUiState
import com.example.shopify.feature.auth.screens.common.components.ErrorCard
import com.example.shopify.feature.auth.screens.login.viewModel.LoginViewModel
import com.example.shopify.utils.shopifyLoading
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigateToSignUp:()->Unit,
    navigateToHome:()->Unit,
    onCloseScreen:()->Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val errorUiState by viewModel.uiErrorState.collectAsState()
    val loadingUiState by viewModel.uiLoadingState.collectAsState()

    LaunchedEffect(key1 = Unit){
        viewModel.uiEvent
            .onEach {authUIEvent ->
                when(authUIEvent){
                    is AuthUIEvent.NavigateToHome -> {navigateToHome()}
                }
            }
            .launchIn(this)
    }
    LoginScreenContent(
        uiState = uiState,
        errorUiState = errorUiState,
        loadingUiState = loadingUiState,
        navigateToSignUp = navigateToSignUp,
        onEmailTextChange = viewModel::sendEmailValue,
        onPasswordTextChange = viewModel::sendPasswordValue,
        onSignIn = viewModel::signIn,
        onCloseScreen = onCloseScreen
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    errorUiState: ErrorAuthUiState,
    navigateToSignUp: () -> Unit,
    loadingUiState:Boolean,
    onEmailTextChange:(String) -> Unit,
    onPasswordTextChange:(String) -> Unit,
    onSignIn:() ->Unit,
    onCloseScreen:()->Unit
) {
    Column {
        AnimatedVisibility(
            visible = errorUiState.isError,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top, animationSpec = tween(1500)),
        ) {
            ErrorCard(error = errorUiState.error)
        }
        Column(
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )
            AuthHeader(onCloseScreen)
            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            Text(
                text = stringResource(id = R.string.login_welcome_expression),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 3.dp)
            )

            Spacer(modifier = Modifier.padding(vertical = 20.dp))


            AuthTextField(
                text = uiState.email.value,
                textFieldHeader = R.string.email,
                placeHolder = R.string.email_place_holder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                isError =  uiState.email.error  != null,
                errorMessage =  uiState.email.error ?: R.string.nothing,
                onTextChanged = { textEmailValue -> onEmailTextChange(textEmailValue) }
            )

            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            AuthPasswordTextField(
                text = uiState.password.value,
                textFieldHeader = R.string.password,
                placeHolder = R.string.password_place_holder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                isError =  uiState.password.error != null,
                errorMessage =  uiState.password.error ?: R.string.nothing,
                onTextChanged = {textPasswordValue -> onPasswordTextChange(textPasswordValue) }
            )

            Spacer(modifier = Modifier.padding(vertical = 25.dp))

            Button(
                onClick = {onSignIn()},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .height(50.dp)
                    .shopifyLoading(loadingUiState),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                shape = RoundedCornerShape(5.dp),
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
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .clickable { navigateToSignUp() }
                        .padding(start = 2.dp)
                )
            }
        }
    }

}


@Preview
@Composable
fun PreviewLogin() {
//    LoginScreenContent(
//        uiState = LoginUiState(),
//        errorUiState = ErrorAuthUiState(),
//        navigateToSignUp = { /*TODO*/ },
//        loadingUiState = false,
//        onEmailTextChange = {},
//        onPasswordTextChange = {}
//    ) {
//
//    }
}

