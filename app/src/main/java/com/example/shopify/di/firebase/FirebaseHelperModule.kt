package com.example.shopify.di.firebase

import com.example.shopify.helpers.firestore.mapper.FireStoreMapper
import com.example.shopify.helpers.firestore.mapper.FireStoreMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseHelperModule {

    @Binds
    @Singleton
    abstract fun bindsFireStoreMapper(fireStoreMapperImpl: FireStoreMapperImpl):FireStoreMapper
}