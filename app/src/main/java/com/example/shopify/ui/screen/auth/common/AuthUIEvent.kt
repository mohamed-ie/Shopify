package com.example.shopify.ui.screen.auth.common

sealed class AuthUIEvent<out T : Any>() {
    object Loading : AuthUIEvent<Nothing>()
    class Error(message: String) : AuthUIEvent<Nothing>()
    class NavigateToHome<out T : Any>(val data: T) : AuthUIEvent<T>()
}
