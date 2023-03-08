package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.Specification

sealed class GetSpecificationUiState {
    object Empty: GetSpecificationUiState()
    object Loading: GetSpecificationUiState()
    data class Success(val specification: ArrayList<Specification>): GetSpecificationUiState()
    data class Error(val error: String): GetSpecificationUiState()
}
