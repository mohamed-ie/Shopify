package com.example.shopify.data.stripe.repository

import com.example.shopify.helpers.Resource

interface StripeRepository {
    suspend fun createCustomer(): Resource<String>
    suspend fun createEphemeralKey(customerId: String): Resource<String>
    suspend fun createPaymentIntent(
        customerId: String,
        amount: Int,
        currency: String,
        autoPaymentMethodsEnable: Boolean
    ): Resource<String>
}