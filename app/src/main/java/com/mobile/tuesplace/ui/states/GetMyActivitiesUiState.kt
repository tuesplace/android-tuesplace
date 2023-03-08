package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.AgendaResponseData

sealed class GetMyActivitiesUiState {
    object Empty : GetMyActivitiesUiState()
    data class Success(val activities: List<AgendaResponseData>) : GetMyActivitiesUiState()
    object Loading : GetMyActivitiesUiState()
    data class Error(val exception: String?) : GetMyActivitiesUiState()
}