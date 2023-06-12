package com.example.shopify.utils.shopify

import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.flow


fun GraphClient.enqueue(query: Storefront.QueryRootQuery) = flow {
    val deferred = CompletableDeferred<GraphCallResult<Storefront.QueryRoot>>()
    queryGraph(query).enqueue(deferred::complete)
    emit(deferred.await())
}

fun GraphClient.enqueue(query: Storefront.MutationQuery) = flow {
    val deferred = CompletableDeferred<GraphCallResult<Storefront.Mutation>>()
    mutateGraph(query).enqueue(deferred::complete)
    emit(deferred.await())
}

suspend fun GraphClient.enqueue1(query: Storefront.QueryRootQuery): GraphCallResult<Storefront.QueryRoot> {
    val deferred = CompletableDeferred<GraphCallResult<Storefront.QueryRoot>>()
    queryGraph(query).enqueue(deferred::complete)
    return deferred.await()
}

suspend fun GraphClient.enqueue1(query: Storefront.MutationQuery): GraphCallResult<Storefront.Mutation> {
    val deferred = CompletableDeferred<GraphCallResult<Storefront.Mutation>>()
    mutateGraph(query).enqueue(deferred::complete)
    return deferred.await()
}