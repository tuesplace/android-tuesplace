package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.*
import okhttp3.MultipartBody
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
        getProfileCallback: ProfileService.GetProfileCallback<ProfileResponseData>,
        profileId: String,
    ) {
        retrofit.getProfiles(profileId).enqueue(
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

    override suspend fun putMyProfileAssets(
        getProfileCallback: ProfileService.GetProfileCallback<Unit>,
        profilePic: MultipartBody.Part,
    ) {
        retrofit.putMyProfileAssets(profilePic).enqueue(
            object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>,
                ) {
                    if (response.isSuccessful) {
                        getProfileCallback.onSuccess(Unit)
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }

            }
        )
    }
}