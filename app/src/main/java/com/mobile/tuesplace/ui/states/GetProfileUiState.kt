package com.mobile.tuesplace.ui.states

sealed class GetProfileUiState {
    object Empty : GetProfileUiState()
    object Success : GetProfileUiState()
    object Loading : GetProfileUiState()
    data class Error(val exception: String?) : GetProfileUiState()
}