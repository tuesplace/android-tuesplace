package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.ProfileResponseData

sealed class GetProfileUiState {
    object Empty : GetProfileUiState()
    data class Success(val profile: ProfileResponseData) : GetProfileUiState()
    object Loading : GetProfileUiState()
    data class Error(val exception: String?) : GetProfileUiState()
}