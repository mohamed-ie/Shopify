package com.example.shopify.helpers

sealed interface Resource<out D> {
    class Success<D>(val data: D) : Resource<D>
    class Error(val error: UIError) : Resource<Nothing>
}

fun <I, O> Resource<I>.mapResource(
    transform: (I) -> O
): Resource<O> {
    return run {
        when (this) {
            is Resource.Error -> this
            is Resource.Success -> Resource.Success(transform(data))
        }
    }
}

suspend fun <I, O> Resource<I>.mapSuspendResource(
    transform: suspend (I) -> O
): Resource<O> {
    return when (this) {
        is Resource.Error -> this
        is Resource.Success -> Resource.Success(transform(data))
    }
}

fun <D> Resource<D>.getOrNull() = when (this) {
    is Resource.Error -> null
    is Resource.Success -> data
}


fun <D> Resource<D>.handle(onError: () -> Unit, onSuccess: (D) -> Unit) {
    when (this) {
        is Resource.Error -> onError()
        is Resource.Success -> onSuccess(this.data)
    }
}