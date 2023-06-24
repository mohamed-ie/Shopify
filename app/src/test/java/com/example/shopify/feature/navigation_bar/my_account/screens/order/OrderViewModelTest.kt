//package com.example.shopify.feature.navigation_bar.my_account.screens.order
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.shopify.data.shopify.repository.ShopifyRepository
//import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
//import com.example.shopify.helpers.cart.CreditCardInfoStateHandlerImpl
//import com.example.shopify.ui.bottom_bar.cart.checkout.CheckoutViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.resetMain
//import org.hamcrest.CoreMatchers
//import org.junit.After
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//class OrderViewModelTest {
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var fakerepository: ShopifyRepository
//    private lateinit var orderViewModel: CheckoutViewModel
//
//    @Before
//    fun setup() {
//        fakerepository = FakeShopifyRepositoryImpl()
//        orderViewModel =
////            CheckoutViewModel(
////                fakerepository,
////                Dispatchers.Unconfined
////            )
//
//
//        @After
//        fun end() {
//            Dispatchers.resetMain()
//        }
//
////
////        @Test
////        fun getOrderList_checkNotNull() {
////            // given nothing
////
////            // when get the orderList
////            val result = orderViewModel.orderList.value
////
////            // then assert that order list is not null
////            Assert.assertThat(result.size, CoreMatchers.notNullValue())
////
////        }
////
////        @Test
////        fun onCreditCardEvent_changeFirstName_checkTheyEqual() {
////            // given firstName
////            val firstName = "Rafeef"
////
////            // when change first name
////            orderViewModel.onCreditCardEvent(CreditCardInfoEvent.FirstNameChanged(firstName))
////
////            // then get the firstName and check they are the same
////            val result = orderViewModel.creditCardInfoState.value.firstNameState.value
////            Assert.assertThat(result, CoreMatchers.`is`(firstName))
////
////        }
////
////        @Test
////        fun onCheckoutEvent_paymentMethod_checkTheyEqual() {
////            // given payment method
////            val paymentMethod = PaymentMethod.CashOnDelivery
////
////            // when fire action with the payment method
////            orderViewModel.onCheckoutEvent(CheckoutEvent.PaymentMethodChanged(paymentMethod))
////
////            // then get the result and check they are the same
////            val result = orderViewModel.checkoutState.value.selectedPaymentMethod
////            Assert.assertThat(result, CoreMatchers.`is`(PaymentMethod.CashOnDelivery))
////
////        }
//    }
//}