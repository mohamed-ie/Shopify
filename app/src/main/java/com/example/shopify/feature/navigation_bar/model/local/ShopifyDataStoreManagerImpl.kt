package com.example.shopify.feature.navigation_bar.model.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ShopifyDataStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ShopifyDataStoreManager {
    override suspend fun saveUserInfo(signInUserInfoResult: SignInUserInfoResult) {
        dataStore.edit {userInfoMutablePreference ->
            userInfoMutablePreference[Constants.DataStoreKeys.USER_ACCESS_TOKEN] = signInUserInfoResult.accessToken
            userInfoMutablePreference[Constants.DataStoreKeys.USER_EXPIRED_TOKEN_AT] = signInUserInfoResult.expireTime.toString()
            userInfoMutablePreference[Constants.DataStoreKeys.USER_EMAIL] = signInUserInfoResult.email
            userInfoMutablePreference[Constants.DataStoreKeys.USER_PASSWORD] = signInUserInfoResult.password
        }
    }

    override fun getUserInfo():Flow<SignInUserInfoResult> =
        dataStore.data.map{ preference->
            SignInUserInfoResult(
                preference[Constants.DataStoreKeys.USER_EMAIL] ?: "" ,
                preference[Constants.DataStoreKeys.USER_PASSWORD] ?: "" ,
                preference[Constants.DataStoreKeys.USER_ACCESS_TOKEN] ?: "",
                preference[Constants.DataStoreKeys.USER_EXPIRED_TOKEN_AT] ?: "",
                ""
            )
        }

    override fun getAccessToken():Flow<String?> =
        dataStore.data.map { preference->
            preference[Constants.DataStoreKeys.USER_ACCESS_TOKEN]
        }


}