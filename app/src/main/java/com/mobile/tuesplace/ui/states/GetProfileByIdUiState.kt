package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.ProfileResponseData

sealed class GetProfileByIdUiState {
    object Empty : GetProfileByIdUiState()
    data class Success(val profile: ProfileResponseData) : GetProfileByIdUiState()
    object Loading : GetProfileByIdUiState()
    data class Error(val exception: String?) : GetProfileByIdUiState()
}