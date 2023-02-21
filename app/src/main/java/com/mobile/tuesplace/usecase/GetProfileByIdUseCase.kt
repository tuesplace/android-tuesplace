package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.services.ProfileService

class GetProfileByIdUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(profileCallback: ProfileService.GetProfileCallback<ProfileResponseData>, profileId: String){
        profileService.getProfiles(profileCallback, profileId)
    }
}