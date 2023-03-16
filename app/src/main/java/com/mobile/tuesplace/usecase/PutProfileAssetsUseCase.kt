package com.mobile.tuesplace.usecase

import com.mobile.tuesplace.services.ProfileService
import okhttp3.MultipartBody

class PutProfileAssetsUseCase(private val profileService: ProfileService) {
    suspend operator fun invoke(getProfileCallback: ProfileService.GetProfileCallback<Unit>, profilePic: MultipartBody.Part, profileId: String){
        profileService.putProfileAssets(getProfileCallback = getProfileCallback, profilePic = profilePic, profileId = profileId)
    }
}