package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.ProfileData

sealed class GetProfileByIdUiState {
    object Empty : GetProfileByIdUiState()
    data class Success(val profile: ProfileData) : GetProfileByIdUiState()
    object Loading : GetProfileByIdUiState()
    data class Error(val exception: String?) : GetProfileByIdUiState()
}