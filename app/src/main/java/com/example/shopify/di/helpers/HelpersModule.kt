package com.example.shopify.di.helpers

import com.example.shopify.helpers.validator.auth.UserInputValidator
import com.example.shopify.helpers.validator.auth.UserInputValidatorImpl
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.helpers.CreditCardInfoStateHandler
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.helpers.CreditCardInfoStateHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class HelpersModule {

    @Binds
    @ViewModelScoped
    abstract fun bindUserInputValidator(userInputValidatorImpl: UserInputValidatorImpl): UserInputValidator

    @Binds
    @ViewModelScoped
    abstract fun bindCreditCardInfoStateHelper(creditCardInfoStateHandlerImpl: CreditCardInfoStateHandlerImpl):
            CreditCardInfoStateHandler


}