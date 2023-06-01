package com.example.shopify.ui.screen.common.model

sealed interface ScreenState {
    object Loading : ScreenState
    object Stable : ScreenState
}