package com.mobile.tuesplace.services

import com.mobile.tuesplace.GENERATE_TOKEN_PAIR
import com.mobile.tuesplace.SIGN_IN
import com.mobile.tuesplace.data.AuthData
import com.mobile.tuesplace.data.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST(SIGN_IN)
    fun signIn(@Body authData: AuthData): Call<SignInResponse>

    @POST(GENERATE_TOKEN_PAIR)
    fun generateTokenPair(@Body refreshToken: String): Call<SignInResponse>
}