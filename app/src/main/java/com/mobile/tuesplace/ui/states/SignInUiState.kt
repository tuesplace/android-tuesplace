package com.mobile.tuesplace.ui.states

sealed class SignInUiState {
    object Empty : SignInUiState()
    object Success : SignInUiState()
    object Loading : SignInUiState()
    data class Error(val exception: String?) : SignInUiState()
}