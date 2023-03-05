package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.CommentData

sealed class GetPostCommentsUiState{
    object Empty : GetPostCommentsUiState()
    data class Success(val groups: List<CommentData>) : GetPostCommentsUiState()
    object Loading : GetPostCommentsUiState()
    data class Error(val exception: String?) : GetPostCommentsUiState()
}
