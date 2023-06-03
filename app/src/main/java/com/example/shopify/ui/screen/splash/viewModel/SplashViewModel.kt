package com.example.shopify.ui.screen.splash.viewModel

import androidx.lifecycle.ViewModel
import com.example.shopify.model.repository.ShopifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    repository: ShopifyRepository
):ViewModel() {
    val isLoggedIn:Boolean = runBlocking { repository.isLoggedIn().first()}
}