package com.example.shopify.feature.navigation_bar.my_account.screens.change_password

import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.feature.navigation_bar.my_account.screens.change_phone_number.ChangePhoneNumberEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.change_phone_number.ChangePhoneNumberViewModel
import com.example.shopify.helpers.validator.TextFieldStateValidatorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest


class ChangePasswordViewModelTest {
    private lateinit var viewModel: ChangePasswordViewModel


    @Before
    fun setUp() {
        viewModel = ChangePasswordViewModel(
            FakeShopifyRepositoryImpl(),
            Dispatchers.Unconfined
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun onEvent_PasswordChanged() = runTest {
        //when
        val actual = "123"
        viewModel.onEvent(ChangePasswordEvent.PasswordChanged(actual))
        val expected = viewModel.state.first().password.value

        //then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun onEvent_ConfirmPasswordChanged() = runTest {
        //when
        val actual = "123"
        viewModel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged(actual))
        val expected = viewModel.state.first().confirmPassword.value

        //then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun onEvent_Change() = runTest {
        //give
        viewModel.onEvent(ChangePasswordEvent.PasswordChanged("201120060103"))
        viewModel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged("201120060103"))

        //when
        viewModel.onEvent(ChangePasswordEvent.Change)
        val expected = viewModel.state.first().remoteError

        //then
        Assert.assertNull(expected)
    }

}