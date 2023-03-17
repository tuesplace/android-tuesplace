package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.USER_ID
import com.mobile.tuesplace.data.AuthData
import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.SignInData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthServiceImpl(private val retrofit: ApiServices): AuthService {


    override suspend fun signIn(
        authData: AuthData,
        authCallback: AuthService.AuthCallback
    ) {
        retrofit.signIn(authData).enqueue(
            object : Callback<BaseResponse<SignInData>> {
                override fun onFailure(call: Call<BaseResponse<SignInData>>, t: Throwable) {
                    t.localizedMessage?.let {
                            authCallback.onError(it)
                    }
                }

                override fun onResponse(
                    call: Call<BaseResponse<SignInData>>,
                    response: Response<BaseResponse<SignInData>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            ACCESS_TOKEN = it.response?.accessToken.toString()
                            USER_ID = it.response?.userId.toString()
                            it.response?.let { it1 -> authCallback.onSuccess(it1) }
//                            it.response?.userId?.let { userId ->
//                                it.response?.accessToken?.let { accessToken ->
//                                    it.response?.refreshToken?.let { refreshToken ->
//                                        authenticationManager.createAccount(userId, accessToken, refreshToken)
//                                        it.response?.let { it1 -> authCallback.onSuccess(it1) }
//                                    }
//                                }
//                            }
                        }
                    } else{
                        authCallback.onError(response.message())
                    }
                }
            }
        )
    }

    override suspend fun generateTokenPair(refreshToken: String): BaseResponse<SignInData> {
        return retrofit.generateTokenPair("adsa", refreshToken)
    }

//    override suspend fun generateTokenPair(
//        refreshToken: String) {
//        return retrofit.generateTokenPair(refreshToken).response
////        execute(
////            object : Callback<BaseResponse<SignInData>> {
////                override fun onFailure(call: Call<BaseResponse<SignInData>>, t: Throwable) {
////                    t.localizedMessage?.let {
////                        authCallback.onError(it)
////                    }
////                }
////
////                override fun onResponse(
////                    call: Call<BaseResponse<SignInData>>,
////                    response: Response<BaseResponse<SignInData>>
////                ) {
////                    if (response.isSuccessful) {
////                        response.body()?.let {
////                            it.response?.let { it1 -> authCallback.onSuccess(it1) }
////                        }
////                    } else{
////                        authCallback.onError(response.message())
////                    }
////                }
////            }
////        )
//    }
}
