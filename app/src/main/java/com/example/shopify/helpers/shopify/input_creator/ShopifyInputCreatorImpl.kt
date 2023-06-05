package com.example.shopify.helpers.shopify.input_creator

import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.CurrencyCode
import com.shopify.buy3.Storefront.MailingAddress
import javax.inject.Inject

class ShopifyInputCreatorImpl @Inject constructor() : ShopifyInputCreator {
    override fun createCreditCardPaymentInputV2(
        amount: String,
        currency: CurrencyCode,
        billingAddress: Storefront.MailingAddressInput,
        creditCardVaultToken: String,
        idempotencyKey: String
    ) = Storefront.CreditCardPaymentInputV2(
        Storefront.MoneyInput(amount, currency),
        idempotencyKey,
        billingAddress,
        creditCardVaultToken
    )

    override fun createMailBillingAddress(
        street: String,
        apartmentNumber: String,
        city: String,
        country: String,
        firstName: String,
        lastName: String,
        phone: String,
        state: String,
        zip: String,
        company: String?
    ) = MailingAddress().apply {
        address1 = street
        address2 = apartmentNumber
        province = state
        this.city = city
        this.country = country
        this.firstName = firstName
        this.lastName = lastName
        this.phone = phone
        this.zip = zip
        this.company = company
    }

}