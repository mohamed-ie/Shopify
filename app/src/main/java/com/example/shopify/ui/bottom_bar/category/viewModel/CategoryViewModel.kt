package com.example.shopify.ui.bottom_bar.category.viewModel

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import com.example.shopify.ui.bottom_bar.category.view.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: ShopifyRepository,
) : BaseScreenViewModel() {
    private var _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()


    init {
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        toLoadingScreenState()
        val tags = repository.getProductsTag()
        val types = repository.getProductsType()
        when (tags) {
            is Resource.Success -> {
                _categoryState.update { oldState ->
                    oldState.copy(productTag = tags.data)
                }
            }

            is Resource.Error -> {
                tags.error
                toErrorScreenState()
            }
        }
        when (types) {
            is Resource.Success -> {
                _categoryState.update { oldState ->
                    oldState.copy(productType = types.data)
                }
            }

            is Resource.Error -> {
                types.error
                toErrorScreenState()
            }
        }
        _categoryState.value.run {
            if (productTag.isNotEmpty() && productType.isNotEmpty()) {
                getProductByQuery()
                toStableScreenState()
            }
        }
    }

    private fun getProductByQuery() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getProductsCategory(
                categoryState.value.productType[categoryState.value.selectedProductTypeIndex],
                categoryState.value.productTag[categoryState.value.selectedProductTagIndex]
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        _categoryState.update { oldState ->
                            oldState.copy(productsList = it.data)
                        }
                    }

                    is Resource.Error -> {toErrorScreenState()}
                }
            }
        }
    }

    fun updateProductType(typeIndex: Int) {
        _categoryState.update { oldState ->
            oldState.copy(selectedProductTypeIndex = typeIndex)
        }
        getProductByQuery()

    }

    fun updateProductTag(tagIndex: Int) {
        _categoryState.update { oldState ->
            oldState.copy(selectedProductTagIndex = tagIndex)
        }
        getProductByQuery()
    }

}