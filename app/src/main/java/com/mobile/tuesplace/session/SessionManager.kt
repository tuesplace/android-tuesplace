package com.mobile.tuesplace.session

import androidx.datastore.core.DataStore
import com.mobile.tuesplace.EMPTY_STRING
import com.mobile.tuesplace.data.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

object SessionManager {
    private var currentDataStore: DataStore<AppSettings>? = null
    private var appSettings: Flow<AppSettings>? = null

    private var currentToken: String = EMPTY_STRING
    private var currentRefreshToken: String = EMPTY_STRING

    private var sessionManager: SessionManager? = null

    fun getInstance(dataStore: DataStore<AppSettings>): SessionManager {
        if (sessionManager == null) {
            currentDataStore = dataStore
            appSettings = dataStore.data
            sessionManager = SessionManager
        }
        return sessionManager as SessionManager
    }

    suspend fun setAuthEntities(token: String, refreshToken: String) {
        currentDataStore?.updateData {
            it.copy(
                token = token,
                refreshToken = refreshToken
            )
        }
        currentToken = token
        currentRefreshToken = refreshToken
    }

    private suspend fun fetchAuthToken(): String {
        return appSettings?.first()?.token ?: EMPTY_STRING
    }

    private suspend fun fetchAuthRefreshToken(): String {
        return appSettings?.first()?.refreshToken ?: EMPTY_STRING
    }

    suspend fun setTokens() {
        currentToken = fetchAuthToken()
        currentRefreshToken = fetchAuthRefreshToken()
    }

    fun getToken(): String {
        return currentToken
    }

    fun getRefreshToken(): String {
        return currentRefreshToken
    }
}