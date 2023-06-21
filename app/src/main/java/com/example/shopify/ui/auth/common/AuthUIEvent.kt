package com.example.shopify.ui.auth.common

sealed class AuthUIEvent() {
    class NavigateToHome() : AuthUIEvent()
}
