package com.example.shopify.ui.screen.auth.helper

interface UserInputValidator {
    fun getValidEmail(email: String): Boolean
    fun getValidPassword(password: String): Boolean
    fun getValidPhone(phone: String): Boolean
}