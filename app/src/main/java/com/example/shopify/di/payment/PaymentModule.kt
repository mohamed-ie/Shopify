package com.example.shopify.di.payment

import com.example.shopify.helpers.Resource
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment.PaymentStrategy
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment.ShopifyCreditCardPaymentStrategy
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(ViewModelComponent::class)
abstract class PaymentModule {
    @Binds
    abstract fun bindsShopifyCreditCardPayment(shopifyCreditCardPaymentStrategy: ShopifyCreditCardPaymentStrategy)
            : PaymentStrategy<ShopifyCreditCardPaymentStrategy.ShopifyPaymentInfo, Flow<Resource<ShopifyCreditCardPaymentStrategy.PaymentResult>>>
}