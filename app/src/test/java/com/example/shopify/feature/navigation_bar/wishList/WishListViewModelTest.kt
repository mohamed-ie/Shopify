package com.example.shopify.feature.navigation_bar.wishList

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.ui.wishList.viewModel.WishListViewModel
import com.shopify.graphql.support.ID
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class WishListViewModelTest {

    private lateinit var viewModel: WishListViewModel

    @Before
    fun init() {
        val repository: ShopifyRepository = FakeShopifyRepositoryImpl()
        viewModel = WishListViewModel(
            repository = repository
        )
    }


    @Test
    fun getWishListProducts_noInput_wishListProducts() = runTest {
        //Given
        val expected = 1
        //When
        viewModel.getWishListProducts()
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.productsState.first()
            assertEquals(expected,result.count())
        }
    }

    @Test
    fun removeWishProduct_productId_deletedProduct() = runTest {
        //Given
        val input = ID("hamed")
        //When
        viewModel.removeWishProduct(input)
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.dialogVisibilityState.first()
            assertEquals(true,result)
        }
    }


    @Test
    fun confirmDeletedProduct_noInput_deletedProduct() = runTest {
        //Given
        //When
        viewModel.confirmDeletedProduct()
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.dialogVisibilityState.first()
            assertEquals(false,result)
        }
    }

    @Test
    fun sendContinueShopping_noInput_wishedBottomSheetStateFalse() = runTest {
        //Given
        //When
        viewModel.sendContinueShopping()
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.wishedBottomSheetState.first()
            assertEquals(false,result.expandBottomSheet)
        }
    }

    @Test
    fun addToCart_productId_wishedBottomSheetStateFalse() = runTest {
        //Given
        //When
        viewModel.sendContinueShopping()
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.wishedBottomSheetState.first()
            assertEquals(false,result.expandBottomSheet)
        }
    }

}