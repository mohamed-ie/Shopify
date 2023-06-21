package com.example.shopify.feature.navigation_bar.home.screen.home.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.ui.bottom_bar.home.home.viewModel.HomeViewModel
import com.example.shopify.ui.home.home.viewModel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakerepository: ShopifyRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        fakerepository = FakeShopifyRepositoryImpl()
        homeViewModel = HomeViewModel(fakerepository)
    }

    // runTest
    @Test
    fun getBrandList_brandListSizeIS1() = runTest {
        val result = homeViewModel.brandList.first()
        Assert.assertThat(result.size, CoreMatchers.`is`(1))

    }
}