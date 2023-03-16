package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.services.ProfileService

class EditMyProfileUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(
        getProfileCallback: ProfileService.GetProfileCallback<Unit>,
        editProfileData: EditProfileData,
    ) {
        profileService.editProfile(getProfileCallback, editProfileData)
    }
}