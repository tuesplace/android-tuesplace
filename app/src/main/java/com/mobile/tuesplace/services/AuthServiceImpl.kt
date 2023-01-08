package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.USER_ID
import com.mobile.tuesplace.data.AuthData
import com.mobile.tuesplace.data.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthServiceImpl: AuthService {

    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)

    override suspend fun signIn(
        authData: AuthData,
        authCallback: AuthService.AuthCallback
    ) {
        retrofit.signIn(authData).enqueue(
            object : Callback<SignInResponse> {
                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                    t.localizedMessage?.let {
                            authCallback.onError(it)
                    }
                }

                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            authCallback.onSuccess(it)
                            ACCESS_TOKEN = it.response?.accessToken.toString()
                            USER_ID = it.response?.userId.toString()
                        }
                    } else{
                        authCallback.onError(response.message())
                    }
                }
            }
        )
    }

    override suspend fun generateTokenPair(
        refreshToken: String,
        authCallback: AuthService.AuthCallback
    ) {
        retrofit.generateTokenPair(refreshToken).enqueue(
            object : Callback<SignInResponse> {
                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                    t.localizedMessage?.let {
                        authCallback.onError(it)
                    }
                }

                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            authCallback.onSuccess(it)
                        }
                    } else{
                        authCallback.onError(response.message())
                    }
                }
            }
        )
    }
}