package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.ProfileResponseData

sealed class GetAllProfilesUiState {
    object Empty : GetAllProfilesUiState()
    data class Success(val profiles: List<ProfileResponseData>) : GetAllProfilesUiState()
    object Loading : GetAllProfilesUiState()
    data class Error(val exception: String?) : GetAllProfilesUiState()
}