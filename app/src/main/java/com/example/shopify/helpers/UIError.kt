package com.example.shopify.helpers

sealed class UIError(val message: String) {
    object Canceled : UIError("Canceled")
    object RequestFailed : UIError("Request Failed")
    object NoInternetConnection : UIError("No Internet connection")
    object Unexpected : UIError("Unexpected Error please try again later")
}