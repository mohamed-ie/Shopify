package com.example.shopify.helpers.validator.auth

import com.example.shopify.utils.Constants
import javax.inject.Inject

class UserInputValidatorImpl @Inject constructor() : UserInputValidator {

    override fun getValidEmail(email:String):Boolean {
        val regex = Regex(Constants.RegexPatterns.EMAIL_PATTERN)
        if (email.isBlank())
            return false
        return regex.matches(email)

    }

    override fun getValidPassword(password:String):Boolean{
        val regex = Regex(Constants.RegexPatterns.PASSWORD_PATTERN)
        if (password.isBlank())
            return false
        return regex.matches(password)
    }

    override fun getValidPhone(phone:String):Boolean {
        val regex = Regex(Constants.RegexPatterns.PHONE_PATTERN)
        if (phone.isBlank())
            return false
        return regex.matches(phone)
    }
}