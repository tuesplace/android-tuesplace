package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.services.ProfileService

class GetProfileByIdUseCase(private val profileService: ProfileService) {
    fun invoke(profileCallback: ProfileService.GetProfileCallback<ProfileData>, profileId: String){
        profileService.getProfiles(profileCallback, profileId)
    }
}