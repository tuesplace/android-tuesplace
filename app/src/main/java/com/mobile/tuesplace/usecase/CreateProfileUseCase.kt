package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.ProfileData
import com.mobile.tuesplace.services.ProfileService

class CreateProfileUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(getProfileCallback: ProfileService.GetProfileCallback<Unit>, profileData: ProfileData
    ) {
        profileService.createProfile(getProfileCallback, profileData)
    }
}