package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.services.ProfileService

class GetProfileUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(getProfileCallback: ProfileService.GetProfileCallback<ProfileResponseData>){
        profileService.getProfile(getProfileCallback)
    }
}