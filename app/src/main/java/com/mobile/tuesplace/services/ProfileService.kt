package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.DeleteProfileResponse
import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileData

interface ProfileService {

    fun getProfile(getProfileCallback: GetProfileCallback<ProfileData>)
    fun getProfiles(getProfileCallback: GetProfileCallback<List<ProfileData>>)
    fun editProfile(getProfileCallback: GetProfileCallback<EditProfileData>, profileId: String, editProfileData: EditProfileData)
    fun deleteProfile(getProfileCallback: GetProfileCallback<DeleteProfileResponse>, profileId: String)

    interface GetProfileCallback<ProfileGeneric>{
        fun onSuccess(profileGeneric: ProfileGeneric)
        fun onError(error: String)
    }
}