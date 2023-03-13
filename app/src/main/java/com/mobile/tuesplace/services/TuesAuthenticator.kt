package com.mobile.tuesplace.services

import android.util.Log
import com.mobile.tuesplace.TuesplaceApplication
import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.SignInData
import com.mobile.tuesplace.dataStore
import com.mobile.tuesplace.session.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call

class TuesAuthenticator(): okhttp3.Authenticator, KoinComponent {
    private val authService: AuthService by inject()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.url.toString().endsWith("auth/sign-in")) {
            return null
        }

        var newToken = SessionManager.getInstance(dataStore = TuesplaceApplication.instance.applicationContext.dataStore).getToken()
        var refreshToken = SessionManager.getInstance(dataStore = TuesplaceApplication.instance.applicationContext.dataStore).getRefreshToken()
        var isSuccessful: Boolean = false
        // Refresh your access_token using a synchronous api request
        val callback: AuthService.AuthCallback = object : AuthService.AuthCallback {

            override fun onSuccess(signInResponse: SignInData) {
                isSuccessful = response.isSuccessful
                refreshToken = signInResponse.refreshToken
                newToken = signInResponse.accessToken
            }

            override fun onError(error: String) {
                isSuccessful = false
            }
        }

        authService.generateTokenPair(refreshToken, callback)


        CoroutineScope(Dispatchers.IO).launch {
            SessionManager.getInstance(dataStore = TuesplaceApplication.instance.applicationContext.dataStore)
                .setAuthEntities(newToken, refreshToken)
        }
        return if (isSuccessful) {
//            val newAccessToken: String? = Api.getInstance().token

            // Add new header to rejected request and retry it
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            //Opens login screen if re-authentication fails
            null
        }
    }
}