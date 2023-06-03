package com.example.shopify.ui.screen.auth.registration.model


data class SignUpUserResponseInfo(
    val firstName:String,
    val lastName:String,
    val email:String,
    val phone:String,
    val id:String,
    val error:String?
)
