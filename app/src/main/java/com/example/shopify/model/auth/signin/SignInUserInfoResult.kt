package com.example.shopify.model.auth.signin


data class SignInUserInfoResult(
    val email: String,
    val password: String,
    val accessToken: String,
    val expireTime: String?,
    val error: String,
)
