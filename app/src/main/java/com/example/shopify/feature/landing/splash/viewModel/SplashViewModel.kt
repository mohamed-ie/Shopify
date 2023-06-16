package com.example.shopify.feature.landing.splash.viewModel

import androidx.lifecycle.ViewModel
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


//@HiltViewModel
//class SplashViewModel @Inject constructor(
//    repository: ShopifyRepository
//):ViewModel() {
//    val isLoggedIn:Boolean = runBlocking { repository.isLoggedIn().first()}
//}