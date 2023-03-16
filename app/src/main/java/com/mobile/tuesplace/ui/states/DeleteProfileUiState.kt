package com.mobile.tuesplace.ui.states

sealed class DeleteProfileUiState {
    object Empty : DeleteProfileUiState()
    object Success : DeleteProfileUiState()
    object Loading : DeleteProfileUiState()
    data class Error(val exception: String?) : DeleteProfileUiState()
}
