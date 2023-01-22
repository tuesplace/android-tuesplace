package com.mobile.tuesplace.ui.states


sealed class DeleteGroupUiState {
    object Empty : DeleteGroupUiState()
    object Success : DeleteGroupUiState()
    object Loading : DeleteGroupUiState()
    data class Error(val exception: String?) : DeleteGroupUiState()
}