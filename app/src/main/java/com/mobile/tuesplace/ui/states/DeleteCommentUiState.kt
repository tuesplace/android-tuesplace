package com.mobile.tuesplace.ui.states

sealed class DeleteCommentUiState {
    object Empty : DeleteCommentUiState()
    object Success : DeleteCommentUiState()
    object Loading : DeleteCommentUiState()
    data class Error(val exception: String?) : DeleteCommentUiState()
}
