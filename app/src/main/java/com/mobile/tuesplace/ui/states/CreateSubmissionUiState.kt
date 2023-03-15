package com.mobile.tuesplace.ui.states

sealed class CreateSubmissionUiState {
    object Empty : CreateSubmissionUiState()
    object Success : CreateSubmissionUiState()
    object Loading : CreateSubmissionUiState()
    data class Error(val exception: String?) : CreateSubmissionUiState()
}
