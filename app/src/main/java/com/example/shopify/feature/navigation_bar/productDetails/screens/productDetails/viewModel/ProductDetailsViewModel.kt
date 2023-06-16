package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.navigation_bar.productDetails.ProductDetailsGraph
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Discount
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.AddToCardState
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ReviewsState
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.VariantsState
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.firestore.mapper.decodeProductId
import com.shopify.graphql.support.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    state: SavedStateHandle
) : BaseScreenViewModel() {

    private val _productState = MutableStateFlow(Product())
    val productState = _productState.asStateFlow()

    private val _addToCardState = MutableStateFlow(
        AddToCardState(
            sendSelectedQuantity = this::sendSelectedQuantity,
            openQuantity = this::openQuantitySection,
            closeQuantity = this::closeQuantitySection,
            addToCard = this::addProductToCart,
            continueShopping = this::dismissBottomSheet
        )
    )
    val addToCardState = _addToCardState.asStateFlow()

    private val _variantState =
        MutableStateFlow(VariantsState(selectVariant = this::sendSelectedVariant))
    val variantState = _variantState.asStateFlow()

    private val _reviewState = MutableStateFlow(ReviewsState())
    val reviewState = _reviewState.asStateFlow()

    val productId:ID

    init {
        productId = state.get<String>(ProductDetailsGraph.PRODUCT_DETAILS_SAVE_ARGS_KEY)?.let { productId ->
            productId.decodeProductId().apply {
                getProduct(this.toString())
                getProductReview(this)
            }
        } ?: ID("")
    }


     fun sendFavouriteAction(isFavourite:Boolean) {
        if(isFavourite)
            viewModelScope.launch { repository.removeProductWishListById(productId) }
        else
            viewModelScope.launch { repository.addProductWishListById(productId) }

        _productState.value = _productState.value.copy(isFavourite = !isFavourite)
    }

    private fun getProduct(id: String) {
        repository.getProductDetailsByID(id)
            .onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        toErrorScreenState()
                    }

                    is Resource.Success -> {
                        toStableScreenState()
                        _addToCardState.value = _addToCardState.value.copy(
                            availableQuantity = if (resource.data.totalInventory in 2..5) resource.data.totalInventory else resource.data.totalInventory
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


    private fun getProductReview(productId: ID) =
        viewModelScope.launch {
            repository.getProductReviewById(productId, 4).also { reviews ->
                _reviewState.value = _reviewState.value.copy(
                    reviews = reviews,
                    reviewCount = reviews.count(),
                    ratingCount = reviews.count(),
                    averageRating = "%.1f".format(calculateAverageRating(reviews)).toDouble()
                )
            }

        }


    private fun calDiscount(price: String): Discount {
        val realPrice = price.toFloat()
        return Discount(
            realPrice = "%.2f".format((realPrice + (realPrice * 0.3f))),
            percent = 30
        )
    }

    private fun sendSelectedQuantity(selected: Int) {
        _addToCardState.value = _addToCardState.value.copy(selectedQuantity = selected)
    }

    private fun closeQuantitySection() {
        _addToCardState.value = _addToCardState.value.copy(isOpened = false)
    }

    private fun openQuantitySection() {
        _addToCardState.value = _addToCardState.value.copy(isOpened = true)
    }

    private fun addProductToCart() {
        _addToCardState.value = _addToCardState.value.copy(expandBottomSheet = true)
        _variantState.value.variants[_variantState.value.selectedVariant - 1].id?.let {variantId ->
            viewModelScope.launch {
                repository.addToCart(variantId.toString(),_addToCardState.value.selectedQuantity)
//                when(repository.addToCart(variantId,_addToCardState.value.selectedQuantity)){
//                    is Resource.Error -> {}
//                    is Resource.Success -> {
//                        _addToCardState.value = _addToCardState.value.copy(isAdded = true)
//                        sendTotalCart(repository.getCart())
//                    }
//                }
            }
        }
    }

//    private fun sendTotalCart(response:Resource<Cart?>){
//        when(response){
//            is Resource.Error -> {}
//            is Resource.Success -> {
//                _addToCardState.value = _addToCardState.value.copy(
//                    isTotalPriceLoaded = true,
//                    totalCartPrice = Price(
//                        amount = response.data?.totalPrice?.amount ?: "",
//                        currencyCode = response.data?.totalPrice?.currencyCode?.name ?: ""
//                    )
//                )
//
//            }
//        }
//    }

    private fun sendSelectedVariant(variantIndex: Int) {
        _variantState.value = _variantState.value.copy(selectedVariant = variantIndex)
        //getProduct(_variantState.value.variants[variantIndex - 1].id ?: "")
    }

    private fun dismissBottomSheet() {
        _addToCardState.value = _addToCardState.value.copy(expandBottomSheet = false)
    }

    private fun calculateAverageRating(reviews: List<Review>): Float {
        val oneStarListCount = reviews.count { review -> review.rate == 1.0 }
        val twoStarListCount = reviews.count { review -> review.rate == 2.0 }
        val threeStarListCount = reviews.count { review -> review.rate == 3.0 }
        val fourStarListCount = reviews.count { review -> review.rate == 4.0 }
        val fiveStarListCount = reviews.count { review -> review.rate == 5.0 }
        val numberOfRates =
            (oneStarListCount + twoStarListCount + threeStarListCount + fourStarListCount + fiveStarListCount)
        return (((1 * oneStarListCount) + (2 * twoStarListCount) + (3 * threeStarListCount) + (4 * fourStarListCount) + (5 * fiveStarListCount)) /
                if (numberOfRates != 0) numberOfRates else 1).toFloat()
    }

}