package com.example.shopify.feature.navigation_bar.productDetails.screens.reviews.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.navigation_bar.productDetails.ProductDetailsGraph
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.ReviewsState
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
class ReviewsDetailsViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    state: SavedStateHandle
):BaseScreenViewModel(){
    private val _productState = MutableStateFlow(Product())
    val productState = _productState.asStateFlow()

    private val _reviewState = MutableStateFlow(ReviewsState())
    val reviewState = _reviewState.asStateFlow()


    init {
        state.get<String>(ProductDetailsGraph.REVIEW_DETAILS_SAVE_ARGS_KEY)?.apply {
            getProduct(this.decodeProductId().toString())
            getProductReview(this.decodeProductId())
        }
    }


    private fun getProduct(id:String){
        repository.getProductDetailsByID(id)
            .onEach {resource ->
                when(resource){
                    is Resource.Error -> {toErrorScreenState()}
                    is Resource.Success -> {
                        toStableScreenState()
                        resource.data.apply {
                            _productState.value = _productState.value.copy(title = this.title, vendor = this.vendor, image = this.image)
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getProductReview(productId:ID) =
        viewModelScope.launch {
            repository.getProductReviewById(productId).also {reviews ->
                _reviewState.value = _reviewState.value.copy(
                    reviews = reviews,
                    reviewCount = reviews.count(),
                    ratingCount = reviews.count(),
                    averageRating = "%.1f".format(calculateAverageRating(reviews)).toDouble()
                )
            }

        }

    private fun calculateAverageRating(reviews: List<Review>):Float  {
        val oneStarListCount = reviews.count { review -> review.rate == 1.0 }
        val twoStarListCount = reviews.count { review -> review.rate == 2.0 }
        val threeStarListCount = reviews.count { review -> review.rate == 3.0 }
        val fourStarListCount = reviews.count { review -> review.rate == 4.0 }
        val fiveStarListCount = reviews.count { review -> review.rate == 5.0 }
        val numberOfRates = (oneStarListCount + twoStarListCount + threeStarListCount + fourStarListCount + fiveStarListCount)
        return (((1 * oneStarListCount) + (2 * twoStarListCount) + (3 * threeStarListCount) + (4 * fourStarListCount) + (5 * fiveStarListCount)) /
                if (numberOfRates!= 0) numberOfRates else 1).toFloat()
    }
}