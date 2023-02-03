package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.GroupResponseData

sealed class GetMyGroupsUiState {
    object Empty : GetMyGroupsUiState()
    data class Success(val groups: List<GroupResponseData>) : GetMyGroupsUiState()
    object Loading : GetMyGroupsUiState()
    data class Error(val exception: String?) : GetMyGroupsUiState()
}