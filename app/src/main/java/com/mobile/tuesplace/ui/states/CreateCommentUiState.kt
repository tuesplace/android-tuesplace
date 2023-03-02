package com.mobile.tuesplace.ui.states

sealed class CreateCommentUiState {
    object Empty : CreateCommentUiState()
    object Success : CreateCommentUiState()
    object Loading : CreateCommentUiState()
    data class Error(val exception: String?) : CreateCommentUiState()
}