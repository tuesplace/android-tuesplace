package com.mobile.tuesplace.ui.states

sealed class EditMarkUiState {
    object Empty : EditMarkUiState()
    object Success : EditMarkUiState()
    object Loading : EditMarkUiState()
    data class Error(val exception: String?) : EditMarkUiState()
}
