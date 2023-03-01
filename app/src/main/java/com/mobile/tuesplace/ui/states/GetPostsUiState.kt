package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.PostResponseData

sealed class GetPostsUiState {
    object Empty : GetPostsUiState()
    data class Success(val groups: List<PostResponseData>) : GetPostsUiState()
    object Loading : GetPostsUiState()
    data class Error(val exception: String?) : GetPostsUiState()
}