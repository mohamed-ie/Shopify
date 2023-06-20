package com.example.shopify.feature.navigation_bar.search

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.MainDispatcherRule
import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.search.view.SearchedProductsState
import com.example.shopify.feature.search.viewModel.SearchViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun init() {
        val repository: ShopifyRepository = FakeShopifyRepositoryImpl()
        viewModel = SearchViewModel(
            repository = repository
        )
    }


    @Test
    fun getProductsBySearchKeys_emptyKey_nothing() = runTest {
        //Given
        val expected = SearchedProductsState()
        val input = ""
        //When
        viewModel.getProductsBySearchKeys(input)
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.searchedProductsState.first()
            assertEquals(expected.hasNext,result.hasNext)
        }
    }

    @Test
    fun getProductsByLastCursor_lastCursed_getLastCursorProducts() = runTest {
        //Given
        val expected = false
        //When
        viewModel.getProductsBySearchKeys("Hamed")
        viewModel.getProductsByLastCursor()
        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.searchedProductsState.first()
            assertEquals(expected,result.isLoadingHasNext)
        }
    }


    @Test
    fun onFavourite_productIndex_addedToWishList() = runTest(mainDispatcherRule.testDispatcher) {
        //Given
        val expected = true
        //When

        viewModel.getProductsBySearchKeys("hamed")
        delay(1000)
        viewModel.onFavourite(0)

        //Then
        backgroundScope.launch (UnconfinedTestDispatcher(testScheduler)){
            val result = viewModel.searchedProductsState.first()
            assertEquals(expected, result.productList[0].isFavourite)
        }
    }





}