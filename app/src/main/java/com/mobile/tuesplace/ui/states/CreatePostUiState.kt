package com.mobile.tuesplace.ui.states

sealed class CreatePostUiState {
    object Empty : CreatePostUiState()
    object Success : CreatePostUiState()
    object Loading : CreatePostUiState()
    data class Error(val exception: String?) : CreatePostUiState()
}