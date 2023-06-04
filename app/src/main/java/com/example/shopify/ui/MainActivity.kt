package com.example.shopify.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.shopify.helpers.Resource
import com.example.shopify.model.repository.ShopifyRepository
import com.example.shopify.ui.navigation.graph.ShopifyGraph
import com.example.shopify.ui.theme.ShopifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopifyTheme {
                // A surface container using the 'background' color from the theme
                App()
            }
        }
    }

}

@Composable
private fun App() {
    ShopifyGraph(navController = rememberNavController())
}