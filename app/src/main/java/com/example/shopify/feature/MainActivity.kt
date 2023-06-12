package com.example.shopify.feature

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.shopify.feature.navigation_bar.model.remote.FireStoreManager
import com.example.shopify.feature.navigation_bar.wishList.view.WishListScreen
import com.example.shopify.feature.navigation_bar.wishList.viewModel.WishListViewModel
import com.example.shopify.theme.ShopifyTheme
import com.shopify.graphql.support.ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var fireStoreManager: FireStoreManager


    @SuppressLint("LogNotTimber")
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