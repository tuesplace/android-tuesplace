package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import okhttp3.MultipartBody

interface ProfileService {

    suspend fun getAllProfiles(getProfileCallback: GetProfileCallback<List<ProfileResponseData>>)
    suspend fun getProfile(getProfileCallback: GetProfileCallback<ProfileResponseData>)
    suspend fun getProfiles(getProfileCallback: GetProfileCallback<ProfileResponseData>, profileId: String)
    suspend fun editProfile(getProfileCallback: GetProfileCallback<Unit>, editProfileData: EditProfileData)
    suspend fun deleteProfile(getProfileCallback: GetProfileCallback<Unit>, profileId: String)
    suspend fun putMyProfileAssets(getProfileCallback: GetProfileCallback<Unit>, profilePic: MultipartBody.Part)
    suspend fun createProfile(getProfileCallback: GetProfileCallback<Unit>, profileData: ProfileData)
    suspend fun putProfile(getProfileCallback: GetProfileCallback<Unit>, editProfileData: EditProfileData, profileId: String)
    suspend fun putProfileAssets(getProfileCallback: GetProfileCallback<Unit>, profilePic: MultipartBody.Part, profileId: String)

    interface GetProfileCallback<ProfileGeneric>{
        fun onSuccess(profileGeneric: ProfileGeneric)
        fun onError(error: String)
    }
}