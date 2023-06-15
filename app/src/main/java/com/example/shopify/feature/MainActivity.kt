package com.example.shopify.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.shopify.feature.navigation_bar.model.remote.apiLayerCurrency.ApiLayerCurrencyDto
import com.example.shopify.theme.ShopifyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apiLayerCurrencyDto: ApiLayerCurrencyDto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopifyTheme {
                 //A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    App()
                }


            }
        }
    }
}

@Composable
private fun App() {
    ShopifyGraph(navController = rememberNavController())
}