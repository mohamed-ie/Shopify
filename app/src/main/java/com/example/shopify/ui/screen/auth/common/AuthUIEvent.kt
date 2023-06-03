package com.example.shopify.ui.screen.auth.common

sealed class AuthUIEvent<out T : Any>() {
    class NavigateToHome<out T : Any>(val data: T) : AuthUIEvent<T>()
}
