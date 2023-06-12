package com.example.shopify.utils

import com.example.shopify.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <I, O> Flow<Resource<I>>.mapSuspendResource(
    transform: suspend (I) -> O
): Flow<Resource<O>> {
    return map { resource ->
        when (resource) {
            is Resource.Error -> resource
            is Resource.Success -> Resource.Success(transform(resource.data))
        }
    }
}

fun <I, O> Flow<Resource<I>>.mapResource(
    transform: (I) -> O
): Flow<Resource<O>> {
    return map { resource ->
        when (resource) {
            is Resource.Error -> resource
            is Resource.Success -> Resource.Success(transform(resource.data))
        }
    }
}