package com.example.shopify.feature.navigation_bar.my_account.screens.order

import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.feature.navigation_bar.my_account.screens.order.helpers.CreditCardInfoStateHandlerImpl
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.credit_card_payment.CreditCardInfoEvent
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.PaymentMethod
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.checkout.view.CheckoutEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import org.junit.After
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CheckoutViewModelTest {
    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        viewModel = CheckoutViewModel(
            CreditCardInfoStateHandlerImpl(),
            FakeShopifyRepositoryImpl(),
            Dispatchers.Unconfined
        )
    }


    @After
    fun tearDown() {
    }

    @Test
    fun loadOrderDetails() = runTest {
        //when
        viewModel.loadOrderDetails()

        //then
        Assert.assertNotNull(viewModel.checkoutState.first())
    }


    @Test
    fun onCreditCardEvent_LastNameChanged() = runTest {
        //give
        val expected = "mohamed"

        //when
        viewModel.onCreditCardEvent(CreditCardInfoEvent.LastNameChanged(expected))

        //then
        val actual = viewModel.creditCardInfoState.first().lastNameState.value
        Assert.assertEquals(expected, actual)
    }


    @Test
    fun onCreditCardEvent_CardNumberChanged() = runTest {
        //give
        val expected = "4539800423715676"

        //when
        viewModel.onCreditCardEvent(CreditCardInfoEvent.CardNumberChanged(expected))

        //then
        val actual = viewModel.creditCardInfoState.first().cardNumberState.value
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun onCreditCardEvent_CCVChanged() = runTest {
        //give
        val expected = "676"

        //when
        viewModel.onCreditCardEvent(CreditCardInfoEvent.CCVChanged(expected))

        //then
        val actual = viewModel.creditCardInfoState.first().ccvState.value
        Assert.assertEquals(expected, actual)
    }


    @Test
    fun onCreditCardEvent_ExpireMonthChanged() = runTest {
        //give
        val expected = "06"

        //when
        viewModel.onCreditCardEvent(CreditCardInfoEvent.ExpireMonthChanged(expected))

        //then
        val actual = viewModel.creditCardInfoState.first().expireMonthState.value
        Assert.assertEquals(expected, actual)
    }


    @Test
    fun onCreditCardEvent_ExpireYearChanged() = runTest {
        //give
        val expected = "24"

        //when
        viewModel.onCreditCardEvent(CreditCardInfoEvent.ExpireYearChanged(expected))

        //then
        val actual = viewModel.creditCardInfoState.first().expireYearState.value
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun onCreditCardEvent_Checkout() = runTest {
        //give
        viewModel.onCreditCardEvent(CreditCardInfoEvent.FirstNameChanged("mohamed"))
        viewModel.onCreditCardEvent(CreditCardInfoEvent.LastNameChanged("ibrahim"))
        viewModel.onCreditCardEvent(CreditCardInfoEvent.CardNumberChanged("4539800423715676"))
        viewModel.onCreditCardEvent(CreditCardInfoEvent.ExpireMonthChanged("06"))
        viewModel.onCreditCardEvent(CreditCardInfoEvent.ExpireYearChanged("24"))
        viewModel.onCreditCardEvent(CreditCardInfoEvent.CCVChanged("676"))

        //when
        viewModel.onCreditCardEvent(CreditCardInfoEvent.Checkout)

        //then
        val actual = viewModel.creditCardInfoState.first().remoteError

        Assert.assertNull(actual)
    }

    @Test
    fun onCheckoutEvent_HideInvoiceDialog() = runTest {
        //when
        viewModel.onCheckoutEvent(CheckoutEvent.HideInvoiceDialog)

        //then
        val actual = viewModel.checkoutState.first().isInvoiceDialogVisible

        assertFalse(actual)
    }

    @Test
    fun onCheckoutEvent_PaymentMethodChanged() = runTest {
        //given
        val expected = PaymentMethod.CashOnDelivery

        //when
        viewModel.onCheckoutEvent(CheckoutEvent.PaymentMethodChanged(expected))

        //then
        val actual = viewModel.checkoutState.first().selectedPaymentMethod

        assertEquals(expected, actual)
    }

    @Test
    fun onCheckoutEvent_PlaceOrder() = runTest {
        //given
        viewModel.onCheckoutEvent(CheckoutEvent.PaymentMethodChanged(PaymentMethod.CashOnDelivery))

        //when
        viewModel.onCheckoutEvent(CheckoutEvent.PlaceOrder)

        //then
        val actual = viewModel.checkoutState.first().remoteError

        assertNotNull(actual)
    }
}