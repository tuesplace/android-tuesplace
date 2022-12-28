package com.mobile.tuesplace.ui.states

sealed class CreateGroupUiState {
    object Empty : CreateGroupUiState()
    object Success : CreateGroupUiState()
    object Loading : CreateGroupUiState()
    data class Error(val exception: String?) : CreateGroupUiState()
}