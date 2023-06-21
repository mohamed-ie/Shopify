package com.example.shopify.feature.navigation_bar.my_account.screens.change_phone_number

import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.helpers.validator.TextFieldStateValidatorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ChangePhoneNumberViewModelTest {
    private lateinit var viewModel: ChangePhoneNumberViewModel


    @Before
    fun setUp() {
        viewModel = ChangePhoneNumberViewModel(
            FakeShopifyRepositoryImpl(),
            Dispatchers.Unconfined,
            TextFieldStateValidatorImpl()
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun onEvent_PhoneChanged() = runTest {
        //when
        val actual = "201120060103"
        viewModel.onEvent(ChangePhoneNumberEvent.PhoneChanged(actual))
        val expected = viewModel.state.first().phone.value

        //then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun onEvent_Change() = runTest {
        //give
        viewModel.onEvent(ChangePhoneNumberEvent.PhoneChanged("201120060103"))

        //when
        viewModel.onEvent(ChangePhoneNumberEvent.Change)
        val expected = viewModel.state.first().remoteError

        //then
        Assert.assertNull(expected)
    }

}