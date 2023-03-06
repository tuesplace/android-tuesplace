package com.mobile.tuesplace.ui.states

sealed class EditPostUiState {
    object Empty : EditPostUiState()
    object Success : EditPostUiState()
    object Loading : EditPostUiState()
    data class Error(val exception: String?) : EditPostUiState()
}