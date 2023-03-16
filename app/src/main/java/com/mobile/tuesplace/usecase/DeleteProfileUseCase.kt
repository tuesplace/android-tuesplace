package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.ProfileService

class DeleteProfileUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(getProfileCallback: ProfileService.GetProfileCallback<Unit>, profileId: String) {
        profileService.deleteProfile(getProfileCallback, profileId)
    }
}