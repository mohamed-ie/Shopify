package com.example.shopify.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.shopify.feature.ShopifyGraph
import com.example.shopify.feature.navigation_bar.productDetails.view.ProductDetailsScreen
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
                    id = "gid://shopify/Product/8312390877491",
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