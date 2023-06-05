package com.example.shopify.feature.navigation_bar.cart.view.componenet.coupon

sealed interface CartCouponEvent {
    class ValueChanged(val value: String) : CartCouponEvent

    object Apply : CartCouponEvent
    object Clear : CartCouponEvent
}