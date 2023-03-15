package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.ProfileService
import okhttp3.MultipartBody

class PutMyProfileAssetsUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(getProfileCallback: ProfileService.GetProfileCallback<Unit>, profilePic: MultipartBody.Part){
        profileService.putMyProfileAssets(getProfileCallback = getProfileCallback, profilePic = profilePic)
    }
}