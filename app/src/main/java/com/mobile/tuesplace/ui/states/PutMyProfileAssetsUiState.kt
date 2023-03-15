package com.mobile.tuesplace.ui.states

sealed class PutMyProfileAssetsUiState {
    object Empty: PutMyProfileAssetsUiState()
    object Loading: PutMyProfileAssetsUiState()
    object Success: PutMyProfileAssetsUiState()
    data class Error(val error: String): PutMyProfileAssetsUiState()
}
