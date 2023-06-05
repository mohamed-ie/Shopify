package com.example.shopify.helpers.shopify.input_creator

import com.shopify.buy3.Storefront
import java.util.UUID

interface ShopifyInputCreator {
    fun createMailBillingAddress(
        street: String,
        apartmentNumber: String,
        city: String,
        country: String,
        firstName: String,
        lastName: String,
        phone: String,
        state: String,
        zip: String,
        company: String?=null
    ): Storefront.MailingAddress

    fun createCreditCardPaymentInputV2(
        amount: String,
        currency: Storefront.CurrencyCode,
        billingAddress: Storefront.MailingAddressInput,
        creditCardVaultToken: String,
        idempotencyKey: String = UUID.randomUUID().toString()
    ): Storefront.CreditCardPaymentInputV2
}