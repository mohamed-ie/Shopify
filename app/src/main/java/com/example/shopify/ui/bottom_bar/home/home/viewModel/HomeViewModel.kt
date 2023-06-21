package com.example.shopify.ui.bottom_bar.home.home.viewModel

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import com.example.shopify.model.home.Brand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ShopifyRepository
) : BaseScreenViewModel() {
    private var _brandList = MutableStateFlow<List<Brand>>(emptyList())
    val brandList = _brandList.asStateFlow()


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