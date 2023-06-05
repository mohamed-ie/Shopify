package com.example.shopify.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.shopify.theme.ShopifyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopifyTheme {
                // A surface container using the 'background' color from the theme
                //App()
                ProductDetailsScreen(
                    id = "gid://shopify/Product/8312397005107",
                    viewModel = hiltViewModel()
                )
            }
        }
    }

}

@Composable
private fun App() {
    ShopifyGraph(navController = rememberNavController())
}