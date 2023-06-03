package com.example.shopify.ui.screen.auth.common

sealed class AuthUIEvent() {
    class NavigateToHome() : AuthUIEvent()
}
