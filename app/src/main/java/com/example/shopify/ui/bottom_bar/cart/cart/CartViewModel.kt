package com.example.shopify.ui.bottom_bar.cart.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.example.shopify.base.BaseScreenViewModel
import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.di.DefaultDispatcher
import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.handle
import com.example.shopify.model.cart.cart.Cart
import com.example.shopify.ui.bottom_bar.cart.cart.view.CartState
import com.example.shopify.ui.bottom_bar.cart.cart.view.componenet.cart_item_card.CartItemEvent
import com.example.shopify.ui.bottom_bar.cart.cart.view.componenet.cart_item_card.CartLineState
import com.example.shopify.ui.bottom_bar.cart.cart.view.componenet.coupon.CartCouponEvent
import com.example.shopify.ui.bottom_bar.cart.cart.view.componenet.coupon.CartCouponState
import com.shopify.graphql.support.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ShopifyRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    private val _cartLinesState = MutableStateFlow(mutableStateListOf<CartLineState>())
    val cartLinesState = _cartLinesState.asStateFlow()

    private val _couponState = MutableStateFlow(CartCouponState())
    val couponState = _couponState.asStateFlow()

    init {
        checkIsLoggedIn()
    }

    fun loadCart() = viewModelScope.launch(defaultDispatcher) {
        toLoadingScreenState()
        handleCartResource(repository.getCart())
    }

    private fun handleCartResource(resource: Resource<Cart?>) =
        when (resource) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> {
                resource.data?.run {
                    _state.update { it.copy(cart = this) }
                    _cartLinesState.update { List(this.lines.size) { CartLineState() }.toMutableStateList() }
                    _couponState.update { it.copy(errorVisible = this.couponError != null) }
                }
                toStableScreenState()
            }
        }

    private fun checkIsLoggedIn() {
        repository.isLoggedIn()
            .onEach { isLoggedIn -> _state.update { it.copy(isLoggedIn = isLoggedIn) } }
            .launchIn(viewModelScope)
    }

    fun onCartItemEvent(event: CartItemEvent) {
        when (event) {
            is CartItemEvent.MoveToWishlist ->
                removeCartLineAndAddToWishList(index = event.index)

            is CartItemEvent.QuantityChanged ->
                changeCartLineQuantity(event.index, event.quantity)

            is CartItemEvent.RemoveLine ->
                removeCartLine(event.index)

            is CartItemEvent.ToggleQuantitySelectorVisibility ->
                _cartLinesState.update(event.index) { it.copy(isChooseQuantityOpen = it.isChooseQuantityOpen.not()) }

        }
    }

    private fun addCartItemToWishList(productID: ID) {
        viewModelScope.launch {
            repository.addProductWishListById(productID)
        }
    }

    private fun changeCartLineQuantity(index: Int, quantity: Int) =
        viewModelScope.launch(defaultDispatcher) {
            _cartLinesState.update(index) {
                it.copy(
                    isChangingQuantity = true,
                    isChooseQuantityOpen = false
                )
            }
            val cartLineId = _state.value.cart.lines[index].productVariantID.toString()
            when (val resource = repository.changeCartLineQuantity(cartLineId, quantity)) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> handleCartResource(resource)
            }
        }

    private fun removeCartLine(index: Int) = viewModelScope.launch(defaultDispatcher) {
        _cartLinesState.update(index) { it.copy(isRemoving = it.isRemoving.not()) }
        val cartLineId = _state.value.cart.lines[index].productVariantID.toString()
        when (val resource = repository.removeCartLines(cartLineId)) {
            is Resource.Error -> toErrorScreenState()
            is Resource.Success -> handleCartResource(resource)
        }
    }

    private fun removeCartLineAndAddToWishList(index: Int) =
        viewModelScope.launch(defaultDispatcher) {
            _cartLinesState.update(index) { it.copy(isMovingToWishlist = it.isMovingToWishlist.not()) }
            val cartLineId = _state.value.cart.lines[index].id.toString()
            when (val resource = repository.removeCartLines(cartLineId)) {
                is Resource.Error -> toErrorScreenState()
                is Resource.Success -> {
                    _state.value.cart.lines[index].cartProduct.id.let { productId ->
                        addCartItemToWishList(
                            productId
                        )
                    }
                    handleCartResource(resource)
                }
            }
        }

    fun onCouponEvent(event: CartCouponEvent) {
        when (event) {
            CartCouponEvent.Apply -> {
                applyCoupon()
            }

            CartCouponEvent.Clear ->
                _couponState.update { it.copy(coupon = "") }

            is CartCouponEvent.ValueChanged ->
                _couponState.update { it.copy(coupon = event.value) }
        }
    }

    private fun applyCoupon() = viewModelScope.launch(defaultDispatcher) {
        _couponState.update { it.copy(errorVisible = false, isLoading = true) }
        repository.applyCouponToCart(couponState.value.coupon).handle(
            onError = ::toErrorScreenState,
            onSuccess = { data ->
                if (data == null)
                    _couponState.update { it.copy(errorVisible = true, isLoading = false) }
                else {
                    _state.update { it.copy(cart = data) }
                    _cartLinesState.update { List(data.lines.size) { CartLineState() }.toMutableStateList() }
                }
                _couponState.update { it.copy(isLoading = false) }
                toStableScreenState()
            }
        )

    }
}

private fun MutableStateFlow<SnapshotStateList<CartLineState>>.update(
    index: Int,
    state: (oldState: CartLineState) -> CartLineState
) = update { oldList ->
    oldList.apply { add(index, removeAt(index).run { state(this) }) }
}

