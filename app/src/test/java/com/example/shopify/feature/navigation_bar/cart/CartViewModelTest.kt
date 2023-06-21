package com.example.shopify.feature.navigation_bar.cart

import com.example.shopify.MainDispatcherRule
import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.ui.shopping_bag.screen.cart.CartViewModel
import com.example.shopify.ui.shopping_bag.screen.cart.view.componenet.cart_item_card.CartItemEvent
import com.example.shopify.ui.shopping_bag.screen.cart.view.componenet.coupon.CartCouponEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CartViewModelTest {
    private lateinit var cartViewModel: CartViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setUp() {
        cartViewModel = CartViewModel(FakeShopifyRepositoryImpl(), Dispatchers.Unconfined)
        cartViewModel.loadCart()
        cartViewModel.onCouponEvent(CartCouponEvent.ValueChanged("FK_OFF"))
    }

    @After
    fun tearDown() {

    }


    @Test
    fun loadCart() = runTest {
        //then
        val actual = cartViewModel.state.first()
        assertNotNull(actual)
    }

    @Test
    fun onCartItemEvent_changeQuantity() = runTest {
        //give
        val index = 0
        val quantity = 2

        //when
        cartViewModel.onCartItemEvent(CartItemEvent.QuantityChanged(index, quantity))

        //then
        val result = cartViewModel.state.first().cart.lines[index].quantity
        assertEquals(result, quantity)
    }

    @Test
    fun onCartItemEvent_RemoveItem() = runTest {
        //give
        val index = 0

        //when
        val sizeBeforeRemove = cartViewModel.state.first().cart.lines.size
        cartViewModel.onCartItemEvent(CartItemEvent.RemoveLine(index))

        //then
        val sizeAfterRemove = cartViewModel.state.first().cart.lines.size
        val expected = sizeBeforeRemove - 1

        assertEquals(expected, sizeAfterRemove)
    }

    @Test
    fun onCartItemEvent_MoveToWishList() = runTest {
        val index = 0

        //when
        val sizeBeforeRemove = cartViewModel.state.first().cart.lines.size
        cartViewModel.onCartItemEvent(CartItemEvent.MoveToWishlist(index))

        //then
        val sizeAfterRemove = cartViewModel.state.first().cart.lines.size
        val expected = sizeBeforeRemove - 1

        assertEquals(expected, sizeAfterRemove)
    }

    @Test
    fun onCartItemEvent_ToggleQuantitySelectorVisibility() = runTest{
        val index = 0

        //when
        cartViewModel.onCartItemEvent(CartItemEvent.ToggleQuantitySelectorVisibility(index))

        //then
        val expected = cartViewModel.cartLinesState.first()[index].isChooseQuantityOpen
        val actual = true

        assertEquals(expected, actual)
    }

    @Test
    fun onCouponEvent_clear() = runTest {
        //when
        cartViewModel.onCouponEvent(CartCouponEvent.Clear)

        //then
        val expected = ""
        val actual =cartViewModel.couponState.first().coupon

        assertEquals(expected, actual)
    }

    @Test
    fun onCouponEvent_valueChanged() = runTest {
        //when
        cartViewModel.onCouponEvent(CartCouponEvent.ValueChanged("FK_OFF_AGAIN"))

        //then
        val expected = "FK_OFF_AGAIN"
        val actual =cartViewModel.couponState.first().coupon

        assertEquals(expected, actual)
    }

    @Test
    fun onCouponEvent_Apply() = runTest {
        //when
        cartViewModel.onCouponEvent(CartCouponEvent.Apply)

        //then
        val actual =cartViewModel.state.first().cart.couponError

        assertNull(actual)
    }


}