package com.mobile.tuesplace.services

import com.mobile.tuesplace.data.ProfileData

interface ProfileService {

    fun getProfile(getProfileCallback: GetProfileCallback)


    interface GetProfileCallback{
        fun onSuccess(profileData: ProfileData)
        fun onError(error: String)
    }
}