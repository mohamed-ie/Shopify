package com.example.shopify.feature.navigation_bar.home.screen.product.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.navigtion_bar.screen.shopify.FakeShopifyRepositoryImpl
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakerepository: ShopifyRepository
    private lateinit var productViewModel: ProductViewModel
    private lateinit var stateHandle: SavedStateHandle
    private lateinit var products: List<BrandProduct>

    @Before
    fun setup() {
        stateHandle = SavedStateHandle()
        fakerepository = FakeShopifyRepositoryImpl()
        productViewModel = ProductViewModel(fakerepository, stateHandle)
        products = listOf(
            BrandProduct(
                id = ID("1"),
                title = "title",
                images = listOf(),
                price = Storefront.MoneyV2(),
                isFavourite = false
            ),
            BrandProduct(
                id = ID("2"),
                title = "title",
                images = listOf(),
                price = Storefront.MoneyV2(),
                isFavourite = true
            )
        )
        //   val testDispatcher = TestCoroutineDispatcher()
        // Dispatchers.setMain(testDispatcher)

    }

    @After
    fun end() {
        // Dispatchers.resetMain()
    }

    @Test
    fun getProductList_makeSureBrandProductListNOTNULL() = runTest {
        // given brand name
        // when get the products of brand
        productViewModel.getProduct()

        val result = productViewModel.productList.first().brandProducts

        // assert that brandList is not null
        Assert.assertThat(result.toList(), `is`(notNullValue()))

    }

    @Test
    fun updateSliderValue() {
    }

    @Test
    fun onFavourite_addProductToFav_assertTrue() = runTest {
        // given product not in the wish list
        val product = products[0]
        productViewModel.productList.first().brandProducts.add(product)

        // when add product to favourite List
        productViewModel.onFavourite(
            products.indexOf(
                product
            )
        )
        // assert that it added on wishList
        assertEquals(true, productViewModel.productList.first().brandProducts[0].isFavourite)
    }

    @Test
    fun onFavourite_removeProductToFav_assertTrue() = runTest {
        // given product is in the wish list
        val product = products[1]
        productViewModel.productList.first().brandProducts.add(product)

        // when remove product to favourite List
        productViewModel.onFavourite(
            products.indexOf(
                product
            )
        )
        // assert that it removed from wishList
        assertEquals(true, productViewModel.productList.first().brandProducts[1].isFavourite)
    }
}