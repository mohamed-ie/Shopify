package com.example.shopify.feature.navigation_bar.productDetails.details

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.viewModel.ProductDetailsViewModel
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
class ProductDetailsViewModelTest {

    private lateinit var viewModel: ProductDetailsViewModel

    @Before
    fun init() {
        val repository: ShopifyRepository = FakeShopifyRepositoryImpl()
        viewModel = ProductDetailsViewModel(
            repository = repository,
            SavedStateHandle()
        )
    }


    @Test
    fun loadProductDetails_noInput_productDetails() = runTest {
        //Given
        val expected = "Adidas"
        //When
        viewModel.loadProductDetails()
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.productState.first()
            assertEquals(expected,result.vendor)
        }
    }

    @Test
    fun sendFavouriteAction_isFavouriteValue_addedToWishList() = runTest {
        //Given
        val isFavourite = false
        val expected = true
        //When
        viewModel.sendFavouriteAction(isFavourite)
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.productState.first()
            assertEquals(expected,result.isFavourite)
        }
    }

}