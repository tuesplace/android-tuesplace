package com.mobile.tuesplace.ui.states

sealed class EditProfileUiState {
    object Empty : EditProfileUiState()
    object Success : EditProfileUiState()
    object Loading : EditProfileUiState()
    data class Error(val exception: String?) : EditProfileUiState()
}