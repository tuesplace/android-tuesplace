package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.data.ProfileData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileServiceImpl: ProfileService {

    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)

    override fun getProfile(getProfileCallback: ProfileService.GetProfileCallback) {
        retrofit.getProfile("Bearer $ACCESS_TOKEN").enqueue(
            object : Callback<ProfileData> {
                override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            getProfileCallback.onSuccess(it)
                        }
                    }else{
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }

            }
        )
    }
}