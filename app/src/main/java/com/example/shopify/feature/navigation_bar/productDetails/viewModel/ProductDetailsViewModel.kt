package com.example.shopify.feature.navigation_bar.productDetails.viewModel

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import com.example.shopify.feature.navigation_bar.productDetails.model.Discount
import com.example.shopify.feature.navigation_bar.productDetails.model.Price
import com.example.shopify.feature.navigation_bar.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.view.AddToCardState
import com.example.shopify.feature.navigation_bar.productDetails.view.VariantsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ShopifyRepository
) : BaseScreenViewModel() {

    private val _productState = MutableStateFlow(Product())
    val productState = _productState.asStateFlow()

    private val _addToCardState = MutableStateFlow(
        AddToCardState(
        sendSelectedQuantity = this::sendSelectedQuantity,
        openQuantity = this::openQuantitySection,
        closeQuantity = this::closeQuantitySection,
        addToCard = this::addProductToCart,
        continueShopping = this::dismissBottomSheet)
    )
    val addToCardState = _addToCardState.asStateFlow()


    private val _variantState = MutableStateFlow(VariantsState(selectVariant = this::sendSelectedVariant))
    val variantState = _variantState.asStateFlow()

    fun getProduct(id:String){
        //toLoadingScreenState()
        repository.getProductDetailsByID(id)
            .onEach {resource ->
                when(resource){
                    is Resource.Error -> {toErrorScreenState()}
                    is Resource.Success -> {
                        toStableScreenState()
                        _addToCardState.value = _addToCardState.value.copy(
                            availableQuantity = if(resource.data.totalInventory in 2..5) resource.data.totalInventory else resource.data.totalInventory
                        )
                        _variantState.value = _variantState.value.copy(
                            variants = resource.data.variants,
                            isLowStock = resource.data.totalInventory <= 5
                        )
                        _productState.value = resource.data.copy(
                            discount = calDiscount(resource.data.price.amount),
                            variants = listOf()
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }


    private fun calDiscount(price:String): Discount {
        val realPrice = price.toFloat()
        return Discount(
            realPrice = (realPrice + (realPrice * 0.3)).toString(),
            percent = 30
        )
    }

    private fun sendSelectedQuantity(selected:Int){
        _addToCardState.value =  _addToCardState.value.copy(selectedQuantity = selected)
    }
    private fun closeQuantitySection(){
        _addToCardState.value =  _addToCardState.value.copy(isOpened = false)
    }
    private fun openQuantitySection(){
        _addToCardState.value =  _addToCardState.value.copy(isOpened = true)
    }
    private fun addProductToCart(){
        _addToCardState.value = _addToCardState.value.copy(
            isAdded = true,
            expandBottomSheet = true,
            totalCartPrice = Price(
                amount = (_productState.value.price.amount.toFloat() * _addToCardState.value.selectedQuantity).toString(),
                currencyCode = _productState.value.price.currencyCode
            )
        )
    }

    private fun sendSelectedVariant(variantIndex:Int){
        _variantState.value = _variantState.value.copy(selectedVariant = variantIndex)
        //getProduct(_variantState.value.variants[variantIndex - 1].id ?: "")
    }

    private fun dismissBottomSheet(){
        _addToCardState.value = _addToCardState.value.copy(expandBottomSheet = false)
    }

}