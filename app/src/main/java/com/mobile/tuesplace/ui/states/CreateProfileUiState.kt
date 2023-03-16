package com.mobile.tuesplace.ui.states

sealed class CreateProfileUiState{
    object Empty : CreateProfileUiState()
    object Success : CreateProfileUiState()
    object Loading : CreateProfileUiState()
    data class Error(val exception: String?) : CreateProfileUiState()
}
