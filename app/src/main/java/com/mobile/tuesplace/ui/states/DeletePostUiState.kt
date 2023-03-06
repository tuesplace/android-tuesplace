package com.mobile.tuesplace.ui.states

sealed class DeletePostUiState {
    object Empty : DeletePostUiState()
    object Success : DeletePostUiState()
    object Loading : DeletePostUiState()
    data class Error(val exception: String?) : DeletePostUiState()
}