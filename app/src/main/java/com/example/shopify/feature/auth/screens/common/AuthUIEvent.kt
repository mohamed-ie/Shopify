package com.example.shopify.feature.auth.screens.common

sealed class AuthUIEvent() {
    class NavigateToHome() : AuthUIEvent()
}
