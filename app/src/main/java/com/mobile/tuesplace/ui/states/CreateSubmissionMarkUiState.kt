package com.mobile.tuesplace.ui.states

sealed class CreateSubmissionMarkUiState {
    object Empty : CreateSubmissionMarkUiState()
    object Success : CreateSubmissionMarkUiState()
    object Loading : CreateSubmissionMarkUiState()
    data class Error(val exception: String?) : CreateSubmissionMarkUiState()
}