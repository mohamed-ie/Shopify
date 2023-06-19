package com.example.shopify.feature.navigation_bar.category.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.feature.navigation_bar.model.repository.shopify.FakeShopifyRepositoryImpl
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CategoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakerepository: ShopifyRepository
    private lateinit var categoryViewModel: CategoryViewModel

    @Before
    fun setup() {
        fakerepository = FakeShopifyRepositoryImpl()
        categoryViewModel = CategoryViewModel(fakerepository)
        val testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun end() {
        Dispatchers.resetMain()
    }


    @Test
    fun getCategoryProducts_productTagAndType_checkProductListSize() {
        // given tage and type index
        categoryViewModel.updateProductTag(1)
        categoryViewModel.updateProductType(1)

        // when get the product list
        val result = categoryViewModel.categoryState.value.productsList

        // assert that product list  size is 2
        Assert.assertThat(result.size, CoreMatchers.`is`(2))
    }

    @Test
    fun updateProductType_newProductTypeIndex_checkTheNewIndex() {
        // given old product type index
        Assert.assertThat(
            categoryViewModel.categoryState.value.selectedProductTypeIndex,
            CoreMatchers.`is`(0)
        )
        // when update product type
        categoryViewModel.updateProductType(1)

        // then get the new product type index
        val result = categoryViewModel.categoryState.value.selectedProductTypeIndex
        Assert.assertThat(result, CoreMatchers.`is`(1))
    }

    @Test
    fun updateProductTag() {
        // given old product tag index
        Assert.assertThat(
            categoryViewModel.categoryState.value.selectedProductTagIndex,
            CoreMatchers.`is`(0)
        )
        // when update product tag
        categoryViewModel.updateProductTag(1)

        // then get the new product type index
        val result = categoryViewModel.categoryState.value.selectedProductTagIndex
        Assert.assertThat(result, CoreMatchers.`is`(1))
    }

}