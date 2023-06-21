package com.example.shopify.model.auth.signup


data class SignUpUserResponseInfo(
    val firstName:String,
    val lastName:String,
    val email:String,
    val phone:String,
    val id:String,
    val error:String?
)
