package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.data.ProfileData

interface ProfileService {

    fun getProfile(getProfileCallback: GetProfileCallback<ProfileData>)
    fun getProfiles(getProfileCallback: GetProfileCallback<List<ProfileData>>)
    fun editProfile(getProfileCallback: GetProfileCallback<Unit>, profileId: String, editProfileData: EditProfileData)
    fun deleteProfile(getProfileCallback: GetProfileCallback<Unit>, profileId: String)

    interface GetProfileCallback<ProfileGeneric>{
        fun onSuccess(profileGeneric: ProfileGeneric)
        fun onError(error: String)
    }
}