package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.services.ProfileService

class EditProfileUseCase(private val profileService: ProfileService) {
    fun invoke(editProfileCallback: ProfileService.GetProfileCallback<Unit>, profileId: String, editProfileData: EditProfileData){
        profileService.editProfile(editProfileCallback, profileId, editProfileData)
    }
}