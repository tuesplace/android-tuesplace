package com.mobile.tuesplace.ui.states

import com.mobile.tuesplace.data.GroupData

sealed class GetGroupUiState {
    object Empty : GetGroupUiState()
    data class Success(val groupData: GroupData) : GetGroupUiState()
    object Loading : GetGroupUiState()
    data class Loaded(val post: GroupData) : GetGroupUiState()
    data class Error(val exception: String?) : GetGroupUiState()
}