package com.mobile.tuesplace.services

import com.mobile.tuesplace.ACCESS_TOKEN
import com.mobile.tuesplace.data.DeleteProfileResponse
import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileServiceImpl : ProfileService {

    private val retrofit = RetrofitHelper.getInstance().create(ApiServices::class.java)

    override fun getProfile(getProfileCallback: ProfileService.GetProfileCallback<ProfileData>) {
        retrofit.getProfile("Bearer $ACCESS_TOKEN").enqueue(
            object : Callback<ProfileData> {
                override fun onResponse(call: Call<ProfileData>, response: Response<ProfileData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            getProfileCallback.onSuccess(it)
                        }
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }

            }
        )
    }

    override fun getProfiles(getProfileCallback: ProfileService.GetProfileCallback<List<ProfileData>>) {
        retrofit.getProfiles("Bearer $ACCESS_TOKEN").enqueue(
            object : Callback<List<ProfileData>> {
                override fun onResponse(
                    call: Call<List<ProfileData>>,
                    response: Response<List<ProfileData>>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { getProfileCallback.onSuccess(it) }
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<List<ProfileData>>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }
            }
        )
    }

    override fun editProfile(
        getProfileCallback: ProfileService.GetProfileCallback<EditProfileData>,
        profileId: String,
        editProfileData: EditProfileData,
    ) {
        retrofit.editProfile("Bearer $ACCESS_TOKEN", profileId, editProfileData = editProfileData)
            .enqueue(
                object : Callback<EditProfileData> {
                    override fun onResponse(
                        call: Call<EditProfileData>,
                        response: Response<EditProfileData>,
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { getProfileCallback.onSuccess(it) }
                        } else {
                            getProfileCallback.onError(response.message())
                        }
                    }

                    override fun onFailure(call: Call<EditProfileData>, t: Throwable) {
                        t.localizedMessage?.let { getProfileCallback.onError(it) }
                    }
                }
            )
    }

    override fun deleteProfile(
        getProfileCallback: ProfileService.GetProfileCallback<DeleteProfileResponse>,
        profileId: String,
    ) {
        retrofit.deleteProfile("Bearer $ACCESS_TOKEN", profileId).enqueue(
            object : Callback<DeleteProfileResponse> {
                override fun onResponse(
                    call: Call<DeleteProfileResponse>,
                    response: Response<DeleteProfileResponse>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { getProfileCallback.onSuccess(it) }
                    } else {
                        getProfileCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<DeleteProfileResponse>, t: Throwable) {
                    t.localizedMessage?.let { getProfileCallback.onError(it) }
                }

            }
        )
    }
}