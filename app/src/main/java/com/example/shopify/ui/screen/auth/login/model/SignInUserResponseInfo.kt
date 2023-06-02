package com.example.shopify.ui.screen.auth.login.model

import org.joda.time.DateTime

data class SignInUserResponseInfo(
    val accessToken:String,
    val expireTime:DateTime?,
    val error:String
)
