package com.example.shopify.feature.navigation_bar.home.screen.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.helpers.Resource
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandViewModel @Inject constructor(
    private val repository: ShopifyRepository
) : ViewModel() {
    private var _brandList = MutableSharedFlow<List<Brand>?>()
    val brandList: SharedFlow<List<Brand>?> = _brandList

    init {
        getBrandList()
      //  getProduct("VANS")
    }

    fun getBrandList() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getBrands().collect {
                when (it) {
                    is Resource.Success -> _brandList.emit(it.data)
                    is Resource.Error -> it.error
                }
            }
        }
    }
//    fun getProduct(brandName:String){
//        viewModelScope.launch(Dispatchers.Default) {
//            repository.getProductsByBrandName(brandName).collect{
//                when (it) {
//                    is Resource.Success -> Log.i("TAG", "getProduct: ")
//                    is Resource.Error -> it.error
//                }
//            }
//        }
//    }
}