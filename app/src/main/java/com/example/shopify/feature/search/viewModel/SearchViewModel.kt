package com.example.shopify.feature.search.viewModel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.feature.search.view.SearchedProductsState
import com.example.shopify.helpers.Resource
import com.example.shopify.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ShopifyRepository,
) : BaseScreenViewModel() {

    private val _searchedProductsState = MutableStateFlow(SearchedProductsState())
    val searchedProductsState = _searchedProductsState.asStateFlow()

    init {
        toStableScreenState()
    }


    fun getProductsBySearchKeys(key: String) {
        _searchedProductsState.update { searchedProductsState ->
            searchedProductsState.copy(
                searchTextValue = key
            )
        }
        toLoadingScreenState()
        viewModelScope.launch(Dispatchers.Default) {
            when (val response =
                repository.getProductsByQuery(Constants.ProductQueryType.TITLE, key)) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    toStableScreenState()
                    _searchedProductsState.update { searchedProductsState ->
                        response.data?.data?.let { brandProducts ->
                            searchedProductsState.copy(
                                productList = brandProducts.toMutableStateList(),
                                lastCursor = response.data.lastCursor,
                                hasNext = response.data.hasNext
                            )
                        } ?: _searchedProductsState.value
                    }
                }
            }
        }
    }


    fun getProductsByLastCursor() {
        if (_searchedProductsState.value.hasNext)
            viewModelScope.launch {
                when (
                    val response = repository.getProductsByQuery(
                        Constants.ProductQueryType.LAST_CURSOR,
                        _searchedProductsState.value.lastCursor
                    )
                ) {
                    is Resource.Error -> toErrorScreenState()
                    is Resource.Success -> {
                        toStableScreenState()
                        _searchedProductsState.update { searchedProductsState ->
                            response.data?.data?.let { brandProducts ->
                                searchedProductsState.copy(
                                    productList = searchedProductsState.productList.plus(
                                        brandProducts
                                    ).toMutableStateList(),
                                    lastCursor = response.data.lastCursor,
                                    hasNext = response.data.hasNext
                                )
                            } ?: _searchedProductsState.value
                        }
                    }
                }
            }
    }


}