package com.example.shopify.feature.navigation_bar.category.viewModel

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.category.model.CategoryState
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: ShopifyRepository,
) : BaseScreenViewModel() {
    private var _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()


    init {
        val tags = repository.getProductsTag()
        val types = repository.getProductsType()
        tags.zip(types) { tag, type ->
            when (tag) {
                is Resource.Success -> {
                    _categoryState.update { oldState ->
                        oldState.copy(productTag = tag.data)
                    }
                }

                is Resource.Error -> tag.error
            }
            when (type) {
                is Resource.Success -> {
                    _categoryState.update { oldState ->
                        oldState.copy(productType = type.data)
                    }
                }

                is Resource.Error -> type.error
            }
            getProductByQuery()
            toStableScreenState()
        }.launchIn(viewModelScope)
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

                    is Resource.Error -> it.error
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