package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.GroupResponseData

sealed class GetGroupsUiState {
    object Empty : GetGroupsUiState()
    data class Success(val groups: List<GroupResponseData>) : GetGroupsUiState()
    object Loading : GetGroupsUiState()
    data class Error(val exception: String?) : GetGroupsUiState()
}