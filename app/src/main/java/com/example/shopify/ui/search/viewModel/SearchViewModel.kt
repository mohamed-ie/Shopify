package com.example.shopify.ui.search.viewModel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import com.example.shopify.ui.search.view.SearchedProductsState
import com.example.shopify.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ShopifyRepository
) : BaseScreenViewModel() {

    private val _searchedProductsState = MutableStateFlow(SearchedProductsState())
    val searchedProductsState = _searchedProductsState.asStateFlow()

    init {
        toStableScreenState()
        viewModelScope.launch {
            _searchedProductsState.update {
                it.copy(isLogged = repository.isLoggedIn().first())
            }
        }
    }

    fun getProductsBySearchKeys(key: String) {
        toLoadingScreenState()
        if (key.isBlank() || key.isEmpty()) {
            _searchedProductsState.update { SearchedProductsState() }
            toStableScreenState()
        } else {
            _searchedProductsState.update { searchedProductsState ->
                searchedProductsState.copy(
                    searchTextValue = key,
                    isLoadingHasNext = true
                )
            }
            viewModelScope.launch(Dispatchers.Default) {
                when (val response =
                    repository.getProductsByQuery(Constants.ProductQueryType.TITLE, key)) {
                    is Resource.Error -> toErrorScreenState()
                    is Resource.Success -> {
                        _searchedProductsState.update { searchedProductsState ->
                            response.data?.data?.let { brandProducts ->
                                searchedProductsState.copy(
                                    productList = brandProducts.toMutableStateList(),
                                    lastCursor = response.data.lastCursor,
                                    hasNext = response.data.hasNext,
                                    isLoadingHasNext = false
                                )
                            } ?: _searchedProductsState.value
                        }
                        toStableScreenState()
                    }
                }
            }
        }
    }

    fun getProductsByLastCursor() {
        if (_searchedProductsState.value.hasNext)
            _searchedProductsState.update {searchedProductsState ->
                searchedProductsState.copy(
                    isLoadingHasNext = true
                )
            }
            viewModelScope.launch(Dispatchers.Main) {
                when (
                    val response = repository.getProductsByQuery(
                        Constants.ProductQueryType.LAST_CURSOR,
                        _searchedProductsState.value.lastCursor
                    )
                ) {
                    is Resource.Error -> toErrorScreenState()
                    is Resource.Success -> {
                        toStableScreenState()
                        response.data?.let { pageInfo ->
                            _searchedProductsState.update { searchedProductsState ->
                                searchedProductsState.copy(
                                    lastCursor = pageInfo.lastCursor,
                                    hasNext = pageInfo.hasNext,
                                    isLoadingHasNext = false
                                )
                            }
                            pageInfo.data.let {brandProducts ->
                                val result = _searchedProductsState.value.productList
                                brandProducts.onEach { brandProduct ->
                                    if (result.find { it.id == brandProduct.id } == null)
                                        result.add(brandProduct)
                                }
                            }

                        } ?: _searchedProductsState.value

                    }
                }
            }
    }



    fun onFavourite(index: Int) {
        viewModelScope.launch {
            _searchedProductsState.value.productList[index].also { brandProduct ->
                if (brandProduct.isFavourite) {
                    repository.removeProductWishListById(brandProduct.id)
                } else
                    repository.addProductWishListById(brandProduct.id)

                _searchedProductsState.update { productState ->
                    productState.productList[index] =
                        productState.productList[index].copy(isFavourite = !brandProduct.isFavourite)
                    productState.copy(productList = productState.productList)
                }
            }
        }
    }
}