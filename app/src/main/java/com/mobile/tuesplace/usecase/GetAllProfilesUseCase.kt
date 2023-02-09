package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.ProfileResponseData
import com.mobile.tuesplace.services.ProfileService

class GetAllProfilesUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(getProfileCallback: ProfileService.GetProfileCallback<List<ProfileResponseData>>){
        profileService.getAllProfiles(getProfileCallback)
    }
}