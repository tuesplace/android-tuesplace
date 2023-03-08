package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.AgendaResponseData

sealed class GetActivitiesUiState {
    object Empty : GetActivitiesUiState()
    data class Success(val activities: List<AgendaResponseData>) : GetActivitiesUiState()
    object Loading : GetActivitiesUiState()
    data class Error(val exception: String?) : GetActivitiesUiState()
}