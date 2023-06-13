package com.example.shopify.feature.navigation_bar.home.screen.home.viewModel

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandViewModel @Inject constructor(
    private val repository: ShopifyRepository
) : BaseScreenViewModel() {
    private var _brandList = MutableStateFlow<List<Brand>>(emptyList())
    val brandList = _brandList.asStateFlow()

    init {
        getBrandList()
    }

    fun getBrandList() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getBrands().collect {
                when (it) {
                    is Resource.Success -> {
                        _brandList.emit(it.data)
                        toStableScreenState()
                    }

                    is Resource.Error -> toErrorScreenState()
                }
            }
        }
    }
}