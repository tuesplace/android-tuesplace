package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData

interface ProfileService {

    suspend fun getAllProfiles(getProfileCallback: GetProfileCallback<List<ProfileResponseData>>)
    suspend fun getProfile(getProfileCallback: GetProfileCallback<ProfileData>)
    suspend fun getProfiles(getProfileCallback: GetProfileCallback<ProfileData>, profileId: String)
    suspend fun editProfile(getProfileCallback: GetProfileCallback<Unit>, profileId: String, editProfileData: EditProfileData)
    suspend fun deleteProfile(getProfileCallback: GetProfileCallback<Unit>, profileId: String)

    interface GetProfileCallback<ProfileGeneric>{
        fun onSuccess(profileGeneric: ProfileGeneric)
        fun onError(error: String)
    }
}