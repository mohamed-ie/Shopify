package com.example.shopify.base

import androidx.lifecycle.ViewModel
import com.example.shopify.ui.common.state.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class BaseScreenViewModel : ViewModel() {
    private val _screenState = MutableStateFlow(ScreenState.LOADING)
    val screenState = _screenState.asStateFlow()

    protected fun toLoadingScreenState() {
        _screenState.update { ScreenState.LOADING }
    }

    protected fun toErrorScreenState() {
        _screenState.update { ScreenState.ERROR }
    }

    protected fun toStableScreenState() {
        _screenState.update { ScreenState.STABLE }
    }
}
