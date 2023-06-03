package com.example.shopify.ui.screen.auth.login.model


data class SignInUserInfoResult(
    val email:String,
    val password:String,
    val accessToken:String,
    val expireTime:String?,
    val error:String
)
