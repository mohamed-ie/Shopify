package com.example.shopify.data.stripe

import com.example.shopify.BuildConfig
import com.example.shopify.data.stripe.response.CreateEphemeralKeyResponse
import com.example.shopify.data.stripe.response.CreatePaymentIntentResponse
import com.example.shopify.data.stripe.response.StripeCustomerResponse
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface StripeApiClient {
    companion object {
        const val BASE_URL = "https://api.stripe.com/"
        private const val version = "2022-11-15"
    }


    @Headers(
        "Authorization: Bearer ${BuildConfig.STRIPE_SECRET}",
        "Stripe-Version: $version"
    )
    @POST("v1/customers")
    suspend fun createCustomer(): StripeCustomerResponse

    @Headers(
        "Authorization: Bearer ${BuildConfig.STRIPE_SECRET}",
        "Stripe-Version: $version"
    )
    @POST("v1/ephemeral_keys")
    suspend fun createEphemeralKey(
        @Query("customer") customerId: String
    ): CreateEphemeralKeyResponse


    @Headers("Authorization: Bearer ${BuildConfig.STRIPE_SECRET}")
    @POST("v1/payment_intents")
    suspend fun createPaymentIntent(
        @Query("customer") customerId: String,
        @Query("amount") amount: Int,
        @Query("currency") currency: String,
        @Query("automatic_payment_methods[enabled]") autoPaymentMethodsEnable: Boolean,
    ): CreatePaymentIntentResponse

}