package com.example.shopify.feature.navigation_bar.model.repository.apiLayerExhange

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.feature.navigation_bar.model.local.ShopifyDataStoreManager
import com.example.shopify.feature.navigation_bar.model.local.ShopifyDataStoreManagerImpl
import com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency.ApiLayerCurrencyApiClient
import com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency.ApiLayerCurrencyInterceptor
import com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange.ApiLayerExchangeRepository
import com.example.shopify.feature.navigation_bar.model.repository.apiLayerExChange.ApiLayerExchangeRepositoryImpl
import com.example.shopify.utils.Constants
import com.shopify.buy3.Storefront.CurrencyCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ApiLayerExchangeRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ApiLayerExchangeRepository
    private lateinit var shopifyDataStoreManager: ShopifyDataStoreManager
    private lateinit var coroutineScopeTest: TestScope


    @Before
    fun init() {
        val interceptor = ApiLayerCurrencyInterceptor()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.ApiLayerCurrency.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val currencyDto = retrofit.create(ApiLayerCurrencyApiClient::class.java)

        val context: Context = ApplicationProvider.getApplicationContext()
        val coroutineDispatcherTest = StandardTestDispatcher()
        coroutineScopeTest = TestScope(coroutineDispatcherTest + Job())
        val dataStore = PreferenceDataStoreFactory.create(
            scope = coroutineScopeTest,
            produceFile =
            { context.preferencesDataStoreFile(Constants.DataStoreKeys.USER) }
        )
        shopifyDataStoreManager = ShopifyDataStoreManagerImpl(dataStore)
        repository = ApiLayerExchangeRepositoryImpl(shopifyDataStoreManager, currencyDto,Dispatchers.Unconfined)
    }

//
//    @Test
//    fun updateCurrentLiveCurrencyAmount_noInputs_currencyUpdated() = runTest {
//        //Given
//        val expected = 0.118864f
//
//        //When
//        val apiResult = repository.getLiveCurrencyExchange(CurrencyCode.AED.toString()).first()
//
//        coroutineScopeTest.runTest {
//            //Then
//            shopifyDataStoreManager.setCurrencyAmountPerOnePound(apiResult)
//            val result = shopifyDataStoreManager.getCurrencyAmountPerOnePound().first()
//            assertEquals(expected.toInt(), result.toInt())
//        }
//    }


    @Test
    fun changeCurrencyCode_noInputs_currencyUpdated() = runTest {
        //given
        val actual = CurrencyCode.AED.toString()
        repository.changeCurrencyCode(actual)

        //then
        val expected = repository.currentCurrency.first()

        //Then
        assertEquals(expected.toInt(),actual)
    }


}