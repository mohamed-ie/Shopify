package com.example.shopify.ui.order

import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.model.cart.order.Order
import com.example.shopify.ui.order.review.ReviewState
import com.example.shopify.ui.order.review.ReviewUIEvent
import com.example.shopify.ui.product_details.product_details.view.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    private var _orderList = MutableStateFlow<List<Order>>(emptyList())
    val orderList = _orderList.asStateFlow()
    var orderIndex: Int = 0

    private val _reviewState = MutableStateFlow(ReviewState())
    val reviewState = _reviewState.asStateFlow()

    init {
        getOrders()
    }


    fun onReviewEvent(event: ReviewUIEvent) {
        when (event) {
            is ReviewUIEvent.SendContent ->
                _reviewState.update { it.copy(reviewContent = event.value) }

            is ReviewUIEvent.SendRate ->
                _reviewState.update { it.copy(rating = event.value.toDouble()) }

            is ReviewUIEvent.SendTitle ->
                _reviewState.update { it.copy(title = event.value) }

            ReviewUIEvent.Submit ->
                saveReview()

            ReviewUIEvent.Close ->
                _reviewState.value = ReviewState()

            is ReviewUIEvent.View -> {
                _reviewState.update {
                    it.copy(
                        visible = true,
                        orderIndex = event.orderIndex,
                        lineIndex = event.lineIndex
                    )
                }
            }

        }
    }


    private fun saveReview() {
        viewModelScope.launch {
            _reviewState.update { it.copy(isLoading = true) }
            _reviewState.value.let {
                _orderList.value[it.orderIndex].let { order ->
                    order.lineItems[it.lineIndex].let { lineItems ->
                        when (repository.setProductReview(
                            lineItems.id, Review(
                                reviewer = order.firstName + " " + order.lastName,
                                rate = it.rating,
                                review = it.title,
                                description = it.reviewContent
                            )
                        )) {
                            is Resource.Error -> toErrorScreenState()
                            is Resource.Success -> {
                                onReviewEvent(ReviewUIEvent.Close)
                            }
                        }
                    }
                }
            }

        }
    }

    private fun getOrders() {
        viewModelScope.launch(defaultDispatcher) {
            repository.getOrders().collect {
                when (it) {
                    is Resource.Success -> {
                        _orderList.emit(it.data.reversed())
                        toStableScreenState()
                    }

                    is Resource.Error -> toErrorScreenState()
                }
            }
        }
    }
}