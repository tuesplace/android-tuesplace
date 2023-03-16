package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.GroupResponseData

sealed class GetGroupUiState {
    object Empty : GetGroupUiState()
    data class Success(val groupData: GroupResponseData) : GetGroupUiState()
    object Loading : GetGroupUiState()
    data class Loaded(val post: GroupResponseData) : GetGroupUiState()
    data class Error(val exception: String?) : GetGroupUiState()
}