package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.SubmissionData

sealed class GetPostSubmissionsUiState{
    object Empty : GetPostSubmissionsUiState()
    data class Success(val groups: List<SubmissionData>) : GetPostSubmissionsUiState()
    object Loading : GetPostSubmissionsUiState()
    data class Error(val exception: String?) : GetPostSubmissionsUiState()
}
