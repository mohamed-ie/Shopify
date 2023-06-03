package com.example.shopify.helpers

interface UserInputValidator {
    fun getValidEmail(email: String): Boolean
    fun getValidPassword(password: String): Boolean
    fun getValidPhone(phone: String): Boolean
}