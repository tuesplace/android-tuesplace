package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileServiceImpl : ProfileService {

    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)
    override suspend fun getAllProfiles(getProfileCallback: ProfileService.GetProfileCallback<List<ProfileResponseData>>) {
        retrofit.getAllProfiles("Bearer $ACCESS_TOKEN").enqueue(
            object : Callback<BaseResponse<List<ProfileResponseData>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<ProfileResponseData>>>,
                    response: Response<BaseResponse<List<ProfileResponseData>>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { getProfileCallback.onSuccess(it) }
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<List<ProfileResponseData>>>,
                    t: Throwable,
                ) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }
            })
    }

    override suspend fun getProfile(getProfileCallback: ProfileService.GetProfileCallback<ProfileData>) {
        retrofit.getProfile("Bearer $ACCESS_TOKEN").enqueue(
            object : Callback<BaseResponse<ProfileData>> {
                override fun onResponse(
                    call: Call<BaseResponse<ProfileData>>,
                    response: Response<BaseResponse<ProfileData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { getProfileCallback.onSuccess(it) }
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<ProfileData>>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }

            }
        )
    }

    override suspend fun getProfiles(
        getProfileCallback: ProfileService.GetProfileCallback<ProfileData>,
        profileId: String,
    ) {
        retrofit.getProfiles("Bearer $ACCESS_TOKEN", profileId).enqueue(
            object : Callback<BaseResponse<ProfileData>> {
                override fun onResponse(
                    call: Call<BaseResponse<ProfileData>>,
                    response: Response<BaseResponse<ProfileData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { getProfileCallback.onSuccess(it) }
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<ProfileData>>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }
            }
        )
    }

    override suspend fun editProfile(
        getProfileCallback: ProfileService.GetProfileCallback<Unit>,
        profileId: String,
        editProfileData: EditProfileData,
    ) {
        retrofit.editProfile("Bearer $ACCESS_TOKEN", profileId, editProfileData = editProfileData)
            .enqueue(
                object : Callback<BaseResponse<Unit>> {
                    override fun onResponse(
                        call: Call<BaseResponse<Unit>>,
                        response: Response<BaseResponse<Unit>>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.response?.let { getProfileCallback.onSuccess(it) }
                        } else {
                            getProfileCallback.onError(response.message())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                        t.localizedMessage?.let { getProfileCallback.onError(it) }
                    }
                }
            )
    }

    override suspend fun deleteProfile(
        getProfileCallback: ProfileService.GetProfileCallback<Unit>,
        profileId: String,
    ) {
        retrofit.deleteProfile("Bearer $ACCESS_TOKEN", profileId).enqueue(
            object : Callback<BaseResponse<Unit>> {
                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { getProfileCallback.onSuccess(it) }
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Unit>>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }

            }
        )
    }
}