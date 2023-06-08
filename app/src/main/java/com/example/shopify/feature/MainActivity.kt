package com.example.shopify.feature

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.shopify.feature.ShopifyGraph
import com.example.shopify.feature.navigation_bar.model.remote.FireStoreManager
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ProductDetailsScreen
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.theme.ShopifyTheme
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
//                ProductDetailsScreen(
//                    id = "gid://shopify/Product/8312390877491",
//                    viewModel = hiltViewModel(),
//                    back = {}
//                )
//                LaunchedEffect(key1 = Unit){
////                    val review = fireStoreManager.getReviewsByProductId("product_id",3)[0].toString()
////                    Log.i("TAG", "onCreate: $review")
//                    fireStoreManager.setProductReviewByProductId("8312390877491", Review(
//                        reviewer = "AboIsmaiil",
//                        rate = 3.0,
//                        review = "Perfect phone",
//                        description = "Its very nice experience Ga iloved it")
//                    )
//                }
            }
        }
    }

}

@Composable
private fun App() {
    ShopifyGraph(navController = rememberNavController())
}