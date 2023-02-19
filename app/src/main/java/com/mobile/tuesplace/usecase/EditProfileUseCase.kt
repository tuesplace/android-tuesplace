package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.data.EditProfileData
import com.mobile.tuesplace.services.ProfileService

class EditProfileUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(editProfileCallback: ProfileService.GetProfileCallback<Unit>, editProfileData: EditProfileData){
        profileService.editProfile(editProfileCallback, editProfileData)
    }
}