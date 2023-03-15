package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import okhttp3.MultipartBody

interface ProfileService {

    suspend fun getAllProfiles(getProfileCallback: GetProfileCallback<List<ProfileResponseData>>)
    suspend fun getProfile(getProfileCallback: GetProfileCallback<ProfileResponseData>)
    suspend fun getProfiles(getProfileCallback: GetProfileCallback<ProfileResponseData>, profileId: String)
    suspend fun editProfile(getProfileCallback: GetProfileCallback<Unit>, editProfileData: EditProfileData)
    suspend fun deleteProfile(getProfileCallback: GetProfileCallback<Unit>, profileId: String)
    suspend fun putMyProfileAssets(getProfileCallback: GetProfileCallback<Unit>, profilePic: MultipartBody.Part)

    interface GetProfileCallback<ProfileGeneric>{
        fun onSuccess(profileGeneric: ProfileGeneric)
        fun onError(error: String)
    }
}