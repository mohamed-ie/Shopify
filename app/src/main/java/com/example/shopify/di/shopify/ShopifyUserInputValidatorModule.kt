package com.example.shopify.di.shopify

import com.example.shopify.ui.screen.auth.helper.UserInputValidator
import com.example.shopify.ui.screen.auth.helper.UserInputValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ShopifyUserInputValidatorModule {

    @Binds
    @ViewModelScoped
    abstract fun bindUserInputValidator(userInputValidatorImpl: UserInputValidatorImpl):UserInputValidator
}