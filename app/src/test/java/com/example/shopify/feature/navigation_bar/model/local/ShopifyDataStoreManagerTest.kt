package com.example.shopify.feature.navigation_bar.model.local

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ShopifyDataStoreManagerTest {

    private lateinit var shopifyDataStoreManager: ShopifyDataStoreManager
    private lateinit var coroutineScopeTest: TestScope


    @Before
    fun init(){
        val context:Context = ApplicationProvider.getApplicationContext()
        val coroutineDispatcherTest  = StandardTestDispatcher()
        coroutineScopeTest = TestScope(coroutineDispatcherTest + Job())
        val dataStore = PreferenceDataStoreFactory.create(
            scope = coroutineScopeTest,
            produceFile =
            { context.preferencesDataStoreFile(Constants.DataStoreKeys.USER) }
        )
        shopifyDataStoreManager = ShopifyDataStoreManagerImpl(dataStore)
    }
    
    @Test
    fun saveUserInfo_SignInUserInfoResultInstance_insertedData() = coroutineScopeTest.runTest{
        //Given
        val expected = SignInUserInfoResult(
            email = "Hamed@gamil.com",
            password = "hamed@2000",
            accessToken = "758309275kl;ajds;kljfgaiofgvnkl",
            expireTime = "null",
            error = ""
        )
        //When
        shopifyDataStoreManager.saveUserInfo(expected)

        //Then
        val result = shopifyDataStoreManager.getUserInfo().first()
        assertEquals(expected,result)
    }

    @Test
    fun setCurrencyAmountPerOnePound_currencyAmount_savedCurrencyAmount() = coroutineScopeTest.runTest {
        //Given
        val expected = 10f
        //When
        shopifyDataStoreManager.setCurrencyAmountPerOnePound(expected)
        //Then
        val result = shopifyDataStoreManager.getCurrencyAmountPerOnePound().first()
        assertEquals(expected,result)
    }
}