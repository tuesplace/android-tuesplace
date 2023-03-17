package com.mobile.tuesplace.services

import com.mobile.tuesplace.TuesplaceApplication
import com.mobile.tuesplace.dataStore
import com.mobile.tuesplace.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TuesAuthenticator : okhttp3.Authenticator, KoinComponent {
    private val authService: AuthService by inject()

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            try {
                getRequest(response)
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun getRequest(response: Response): Request? {
        if (response.request.url.toString().endsWith("auth/sign-in") ||
            response.request.url.toString().endsWith("generate-token-pair")
        ) {
            if (response.request.url.toString().endsWith("generate-token-pair")) {
                SessionManager.getInstance(dataStore = TuesplaceApplication.instance.applicationContext.dataStore).setEmptyTokens()
            }
            return null
        }

        var newToken = ""
        var refreshToken =
            SessionManager.getInstance(dataStore = TuesplaceApplication.instance.applicationContext.dataStore)
                .getRefreshToken()
        var isSuccessful = false

        val tokenResp = authService.generateTokenPair("Bearer $refreshToken")
        isSuccessful = tokenResp.success
        tokenResp.response?.let { respData ->
            refreshToken = respData.refreshToken
            newToken = respData.accessToken
            SessionManager.getInstance(dataStore = TuesplaceApplication.instance.applicationContext.dataStore)
                .setAuthEntities(newToken, refreshToken)
        }

        return if (isSuccessful) {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null
        }
    }
}