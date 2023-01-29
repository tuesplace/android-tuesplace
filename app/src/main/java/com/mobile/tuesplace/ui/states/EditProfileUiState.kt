package com.mobile.tuesplace.ui.states

sealed class EditProfileUiState {
    object Empty : EditProfileUiState()
    object Success : EditProfileUiState()
    object Loading : GetProfileUiState()
    data class Error(val exception: String?) : EditProfileUiState()
}