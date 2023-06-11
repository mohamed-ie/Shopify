package com.example.shopify.di.firebase

import com.example.shopify.feature.navigation_bar.model.remote.FireStoreManager
import com.example.shopify.feature.navigation_bar.model.remote.FireStoreManagerImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseFireStoreInstance():FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideFireStoreManager(firebaseFireStore: FirebaseFirestore) : FireStoreManager =
        FireStoreManagerImpl(fireStore = firebaseFireStore)
}