package com.example.shopify.feature.navigation_bar.productDetails.reviewing

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.ui.product_details.reviews.viewModel.ReviewsDetailsViewModel
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
class ReviewsDetailsViewModelTest {

    private lateinit var viewModel: ReviewsDetailsViewModel

    @Before
    fun init() {
        val repository: ShopifyRepository = FakeShopifyRepositoryImpl()
        viewModel = ReviewsDetailsViewModel(
            repository = repository,
            SavedStateHandle()
        )
    }


    @Test
    fun loadProductDetails_noInput_productDetails() = runTest {
        //Given
        val expected = 0
        //When
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.reviewState.first()
            assertEquals(expected,result.ratingCount)
        }
    }



}