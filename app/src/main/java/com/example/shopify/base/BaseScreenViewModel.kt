package com.example.shopify.base

import androidx.lifecycle.ViewModel
import com.example.shopify.ui.screen.common.model.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseScreenViewModel : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.LOADING)
    val screenState = _screenState.asStateFlow()
}
