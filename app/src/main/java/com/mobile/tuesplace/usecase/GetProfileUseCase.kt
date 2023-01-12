package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.services.ProfileService

class GetProfileUseCase(private val profileService: ProfileService) {
    fun invoke(getProfileCallback: ProfileService.GetProfileCallback<ProfileData>){
        profileService.getProfile(getProfileCallback)
    }
}