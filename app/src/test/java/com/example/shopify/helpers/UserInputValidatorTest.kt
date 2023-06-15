package com.example.shopify.helpers

import com.example.shopify.helpers.validator.auth.UserInputValidator
import com.example.shopify.helpers.validator.auth.UserInputValidatorImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.annotation.Config
import javax.inject.Inject


class UserInputValidatorTest {

    private lateinit var userInputValidator: UserInputValidator

    @Before
    fun init() {
        userInputValidator = UserInputValidatorImpl()
    }

   @Test
   fun getValidEmail_emailString_validOrNot(){
       //Given
       val expected = true
       val email = "hamed@gmail.com"
       //When
       val result = userInputValidator.getValidEmail(email)
       //Then
       assertEquals(result,expected)
   }

    @Test
    fun getValidPassword_PasswordString_validOrNot(){
        //Given
        val expected = false
        val password = "Hamed@"
        //When
        val result = userInputValidator.getValidPassword(password)
        //Then
        assertEquals(result,expected)
    }

    @Test
    fun getValidPhone_PhoneString_validOrNot(){
        //Given
        val expected = true
        val phone = "+201558653011"
        //When
        val result = userInputValidator.getValidPhone(phone)
        //Then
        assertEquals(result,expected)
    }

}