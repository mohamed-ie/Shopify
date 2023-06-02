package com.example.shopify.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.shopify.ui.navigation.graph.ShopifyGraph
import com.example.shopify.ui.screen.auth.registration.SignUpScreenEvent
import com.example.shopify.ui.theme.ShopifyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopifyTheme {
                // A surface container using the 'background' color from the theme
                //App()
                SignUpScreenEvent()
            }
        }
    }

}

@Composable
private fun App() {
    ShopifyGraph(navController = rememberNavController())
}