package com.mobile.tuesplace.ui.states

sealed class GetGroupsUiState {
    object Empty : GetGroupsUiState()
    object Success : GetGroupsUiState()
    object Loading : GetGroupsUiState()
    data class Error(val exception: String?) : GetGroupsUiState()
}