package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.PostResponseData

sealed class GetPostUiState {
    object Empty : GetPostUiState()
    data class Success(val post: PostResponseData) : GetPostUiState()
    object Loading : GetPostUiState()
    data class Error(val exception: String?) : GetPostUiState()
}