package com.mobile.tuesplace.ui.states

sealed class EditCommentsUiState {
    object Empty : EditCommentsUiState()
    object Success : EditCommentsUiState()
    object Loading : EditCommentsUiState()
    data class Error(val exception: String?) : EditCommentsUiState()
}