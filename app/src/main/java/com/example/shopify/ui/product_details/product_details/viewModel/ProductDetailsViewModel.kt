package com.example.shopify.ui.product_details.product_details.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.firestore.mapper.decodeProductId
import com.example.shopify.model.product_details.Discount
import com.example.shopify.model.product_details.Product
import com.example.shopify.model.product_details.VariantItem
import com.example.shopify.ui.product_details.product_details.view.AddToCardState
import com.example.shopify.ui.product_details.product_details.view.Review
import com.example.shopify.ui.product_details.product_details.view.ReviewsState
import com.example.shopify.ui.product_details.product_details.view.VariantsState
import com.shopify.graphql.support.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Math.abs
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    state: SavedStateHandle,
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

    val productId: ID

    init {
        productId = state.get<String>(com.example.shopify.ui.product_details.ProductDetailsGraph.PRODUCT_DETAILS_SAVE_ARGS_KEY)
            ?.decodeProductId() ?: ID("")
    }


    fun loadProductDetails() {
        getProduct(productId.toString())
    }


    fun sendFavouriteAction(isFavourite: Boolean) = viewModelScope.launch {
        if (isFavourite)
            repository.removeProductWishListById(productId)
        else
            repository.addProductWishListById(productId)

        _productState.value = _productState.value.copy(isFavourite = !isFavourite)
    }

    private fun checkIsLoggedIn(){
        repository.isLoggedIn()
            .onEach {isLoggedIn -> _productState.update { it.copy(isLogged = isLoggedIn) } }
            .launchIn(viewModelScope)
    }

    private fun getProduct(id: String) {
        repository.getProductDetailsByID(id)
            .onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        toErrorScreenState()
                    }

                    is Resource.Success -> {
                        _variantState.value = _variantState.value.copy(
                            variants = mapAnyNegativeQuantity(resource.data.variants),
                            isLowStock = abs(resource.data.totalInventory) <= 5,
                            isAvailable = resource.data.totalInventory != 0
                        )
                        _productState.value = resource.data.copy(
                            discount = calDiscount(resource.data.price.amount),
                            variants = listOf(),
                            totalInventory = resource.data.totalInventory.let { totalInventory ->
                                if (totalInventory < 0)
                                    return@let totalInventory * (-1)
                                return@let totalInventory
                            }
                        )
                        _addToCardState.update {addToCardState ->

                            _variantState.value.let {variantsState ->
                                addToCardState.copy(
                                    availableQuantity =
                                    if (variantsState.variants[variantsState.selectedVariant - 1].availableQuantity in 2..5)
                                        variantsState.variants[variantsState.selectedVariant - 1].availableQuantity - 1
                                    else
                                        variantsState.variants[variantsState.selectedVariant - 1].availableQuantity
                                )
                            }

                        }
                        getProductReview(productId)
                        checkIsLoggedIn()
                        toStableScreenState()
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
        _variantState.value.variants[_variantState.value.selectedVariant - 1].id?.let { variantId ->
            viewModelScope.launch {
                when (val resource = repository.addToCart(variantId.toString(), _addToCardState.value.selectedQuantity)) {
                    is Resource.Error -> toErrorScreenState()
                    is Resource.Success -> {
                        _addToCardState.value = _addToCardState.value.copy(expandBottomSheet = true)
                        _addToCardState.value = _addToCardState.value.copy(isAdded = true)
                        _addToCardState.value = _addToCardState.value.copy(
                            isTotalPriceLoaded = true,
                            totalCartPrice = resource.data?:""
                        )
                    }
                }
            }
        }
    }

    private fun mapAnyNegativeQuantity(variants:List<VariantItem>) : List<VariantItem> =
        variants.map{variantItem ->
            if (variantItem.availableQuantity < 0 )
                return@map variantItem.copy(availableQuantity = variantItem.availableQuantity * (-1))
            return@map variantItem
        }

    private fun sendSelectedVariant(variantIndex: Int) {
        _addToCardState.update {addToCardState ->
            _variantState.value.let {variantsState ->
                _variantState.value = variantsState.copy(selectedVariant = variantIndex)
                addToCardState.copy(
                    availableQuantity =
                    if (variantsState.variants[variantIndex - 1].availableQuantity in 2..5)
                        variantsState.variants[variantIndex - 1].availableQuantity - 1
                    else
                        variantsState.variants[variantIndex - 1].availableQuantity
                )
            }

        }
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