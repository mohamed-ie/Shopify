package com.example.shopify.feature.auth.login

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.feature.auth.screens.common.AuthUIEvent
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.viewModel.LoginViewModel
import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.helpers.validator.auth.UserInputValidator
import com.example.shopify.helpers.validator.auth.UserInputValidatorImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun init() {
        val repository: ShopifyRepository = FakeShopifyRepositoryImpl()
        val userInputValidator: UserInputValidator = UserInputValidatorImpl()
        viewModel = LoginViewModel(
            repository = repository,
            userInputValidator = userInputValidator
        )
    }


    @Test
    fun signIn_UserInfo_createdCustomer() = runTest{
        //Given
        val input = SignInUserInfo(
            email = "hamed@gmail.com",
            password = "111111111111"
        )

        //When
        viewModel.sendEmailValue(input.email)
        viewModel.sendPasswordValue(input.password)
        viewModel.signIn()

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            when(viewModel.uiEvent.first()){
                is AuthUIEvent.NavigateToHome -> assert(true)
            }
        }

    }



    @Test
    fun sendEmailValue_emailString_AddedEmailToState() = runTest {
        //Given
        val expected = "Hamed@gmail.com"
        //When
        viewModel.sendEmailValue(expected)
        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            val result = viewModel.uiState.first()
            Assert.assertEquals(expected,result.email.value)
        }

    }

    @Test
    fun sendPasswordValue_passwordString_AddedPasswordToState() = runTest {
        //Given
        val expected = "1111111"
        //When
        viewModel.sendPasswordValue(expected)
        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            val result = viewModel.uiState.first()
            Assert.assertEquals(expected,result.password.value)
        }

    }


}