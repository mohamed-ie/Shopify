package com.example.shopify.helpers.validator.auth

import dagger.Lazy

interface UserInputValidator : Lazy<UserInputValidator> {
    fun getValidEmail(email: String): Boolean
    fun getValidPassword(password: String): Boolean
    fun getValidPhone(phone: String): Boolean
    override fun get(): UserInputValidator {
        return this
    }
}