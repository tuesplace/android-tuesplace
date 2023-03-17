package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.AuthData
import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.SignInData

interface AuthService {
    suspend fun signIn(authData: AuthData, authCallback: AuthCallback)
    suspend fun generateTokenPair(refreshToken: String): BaseResponse<SignInData>

    interface AuthCallback {
        fun onSuccess(signInResponse: SignInData)
        fun onError(error: String)
    }
}
