package com.mobile.tuesplace.services

import android.accounts.AccountManager
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
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
                            print("HELLO $it")
                            authCallback.onError(it)
                    }
                }

                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            print("HELLO $it")
                            authCallback.onSuccess(it)
                        }
                    } else{
                        print("HELLO ${response.message()}")
                        print(response.raw())
                        print(response.errorBody().toString())
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