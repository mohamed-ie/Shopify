package com.example.shopify.feature.auth.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.data.repository.shopify.FakeShopifyRepository
import com.example.shopify.feature.auth.screens.common.AuthUIEvent
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.auth.screens.registration.viewModel.RegistrationViewModel
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.helpers.validator.auth.UserInputValidator
import com.example.shopify.helpers.validator.auth.UserInputValidatorImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RegistrationViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel:RegistrationViewModel

    @Before
    fun init() {
        val repository: ShopifyRepository = FakeShopifyRepository()
        val userInputValidator: UserInputValidator = UserInputValidatorImpl()
        viewModel = RegistrationViewModel(
            repository = repository,
            userInputValidator = userInputValidator
        )
    }


    @Test
    fun signUp_UserInfo_createdCustomer() = runTest{
        //Given
        val input = SignUpUserInfo(
            firstName = "hamed",
            lastName = "mohamed",
            email = "hamed@gmail.com",
            phone = "+2058653011",
            password = "111111111111"
        )
        //When
        viewModel.sendEmailValue(input.email)
        viewModel.sendPasswordValue(input.password)
        viewModel.sendPhoneValue(input.phone)
        viewModel.sendFirstNameValue(input.firstName)
        viewModel.sendSecondNameValue(input.lastName)
        viewModel.signUp()
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
            assertEquals(expected,result.email.value)
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
            assertEquals(expected,result.password.value)
        }

    }

    @Test
    fun sendPhoneValue_phoneString_AddedPhoneToState() = runTest {
        //Given
        val expected = "+20158653011"
        //When
        viewModel.sendPhoneValue(expected)
        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            val result = viewModel.uiState.first()
            assertEquals(expected,result.phone.value)
        }

    }

    @Test
    fun sendFirstNameValue_firstNameString_AddedFirstNameToState() = runTest {
        //Given
        val expected = "Hamed"
        //When
        viewModel.sendFirstNameValue(expected)
        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            val result = viewModel.uiState.first()
            assertEquals(expected,result.firstName.value)
        }

    }

    @Test
    fun sendLastNameValue_lastNameString_AddedLastNameToState() = runTest {
        //Given
        val expected = "mohamed"
        //When
        viewModel.sendSecondNameValue(expected)
        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            val result = viewModel.uiState.first()
            assertEquals(expected,result.secondName.value)
        }

    }


}