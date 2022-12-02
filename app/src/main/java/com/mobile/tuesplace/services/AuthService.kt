package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.AuthData
import com.mobile.tuesplace.data.SignInResponse

interface AuthService {
    suspend fun signIn(authData: AuthData, authCallback: AuthCallback)
    suspend fun generateTokenPair(refreshToken: String, authCallback: AuthCallback)

    interface AuthCallback {
        fun onSuccess(signInResponse: SignInResponse)
        fun onError(error: String)
    }
}
