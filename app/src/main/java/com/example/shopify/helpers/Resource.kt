package com.example.shopify.helpers

sealed interface Resource<out D> {
    class Success<D>(val data: D) : Resource<D>
    class Error(val error: UIError) : Resource<Nothing>
}