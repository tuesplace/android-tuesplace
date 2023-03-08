package com.mobile.tuesplace.ui.states

sealed class EditSpecificationUiState {
    object Empty: EditSpecificationUiState()
    object Loading: EditSpecificationUiState()
    object Success: EditSpecificationUiState()
    data class Error(val error: String): EditSpecificationUiState()
}