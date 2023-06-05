package com.example.shopify.ui.screen.Product.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.helpers.Resource
import com.example.shopify.model.repository.ShopifyRepository
import com.example.shopify.ui.screen.Product.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ShopifyRepository
) : ViewModel() {
    private var _productList = MutableSharedFlow<List<Product>>()
    val productList: SharedFlow<List<Product>> = _productList

    fun getProduct(brandName: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getProductsByBrandName(brandName).collect {
                when (it) {
                    is Resource.Success -> _productList.emit(it.data)
                    is Resource.Error -> it.error
                }
            }
        }
    }
}