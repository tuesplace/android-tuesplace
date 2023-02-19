package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.BaseResponse
import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileServiceImpl(private val retrofit: ApiServices) : ProfileService {

    override suspend fun getAllProfiles(getProfileCallback: ProfileService.GetProfileCallback<List<ProfileResponseData>>) {
        retrofit.getAllProfiles().enqueue(
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

    override suspend fun getProfile(getProfileCallback: ProfileService.GetProfileCallback<ProfileResponseData>) {
        retrofit.getProfile().enqueue(
            object : Callback<BaseResponse<ProfileResponseData>> {
                override fun onResponse(
                    call: Call<BaseResponse<ProfileResponseData>>,
                    response: Response<BaseResponse<ProfileResponseData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.response?.let { getProfileCallback.onSuccess(it) }
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<BaseResponse<ProfileResponseData>>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }

            }
        )
    }

    override suspend fun getProfiles(
        getProfileCallback: ProfileService.GetProfileCallback<ProfileData>,
        profileId: String,
    ) {
        retrofit.getProfiles(profileId).enqueue(
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
        editProfileData: EditProfileData,
    ) {
        retrofit.editProfile(editProfileData = editProfileData)
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
        retrofit.deleteProfile(profileId).enqueue(
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